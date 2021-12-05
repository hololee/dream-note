package com.hanstudio.dream.dreamnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.Calendar;

/**
 * Created by L on 2015-12-07.
 */
public class ItemViewActivity extends Activity {

    final static String DATABASE_NAME = "dream";
    final static int VER = 1;
    final static String TABLE_NAME = "table1";

    int successData;

    int degree;
    String content;

    Boolean pageChaged;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();

        content = intent.getStringExtra("content");


        TextView contentText = (TextView) findViewById(R.id.content);
        TextView time = (TextView) findViewById(R.id.time);
        TextView text5 = (TextView) findViewById(R.id.text5);
        TextView countText = (TextView) findViewById(R.id.text6);

        ImageView imageView = (ImageView) findViewById(R.id.img);

        Button button_ok = (Button) findViewById(R.id.button_ok);
        Button button_suc = (Button) findViewById(R.id.button_suc);

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (successData == 1) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("page", true);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                } else {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("page", true);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });

        button_suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ItemViewActivity.this);
                builder.setMessage("목표를 달성하셨습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        databaseHelper helper = new databaseHelper(getApplicationContext(), DATABASE_NAME, null, VER);
                        SQLiteDatabase db = helper.getReadableDatabase();

                        db.execSQL("update " + TABLE_NAME + " set success = 1 where content == '" + content + "'");
                        db.close();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("page", true);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);


                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");
        time.setTypeface(typeface);
        contentText.setTypeface(typeface);
        text5.setTypeface(typeface);
        countText.setTypeface(typeface);


        databaseHelper helper = new databaseHelper(getApplicationContext(), DATABASE_NAME, null, VER);
        SQLiteDatabase db = helper.getReadableDatabase();


        String args[] = {content};

        Cursor cursor = db.rawQuery("select success, year, month, date, count, imageurl from " + TABLE_NAME + " where content == ?", args);
        //                                       0      1       2       3         4       5

        cursor.moveToNext();


        successData = cursor.getInt(0);

        int yearData = cursor.getInt(1);
        int monthData = cursor.getInt(2);
        int dateData = cursor.getInt(3);
        int countData = cursor.getInt(4);
        String uriText = cursor.getString(5);
        Uri imageUrl = Uri.parse(cursor.getString(5));

        cursor.close();


        db.execSQL("update " + TABLE_NAME + " set count = " + (++countData) + " where content == '" + content + "'");
        db.close();

        int year = yearData;
        int month = monthData;
        int date = dateData;

        time.setText("등록 날짜\n" + year + "년 " + month + "월 " + date + "일");


        contentText.setText(content);
        countText.setText("" + countData);


        if (successData == 1) {

            button_suc.setVisibility(View.GONE);
        }


        // Toast toast = Toast.makeText(getApplicationContext(),"counts : " + counts ,Toast.LENGTH_LONG);
        // toast.show();

        //이미지 설정
        if (uriText.length() != 4) {

            imageView.setVisibility(View.VISIBLE);

            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 2;


            String path = findPath(imageUrl);

            try {

                ExifInterface exif = new ExifInterface(path);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

                if (orientation != -1) {

                    switch (orientation) {

                        case ExifInterface.ORIENTATION_NORMAL:
                            degree = 0;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            degree = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            degree = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            degree = 270;
                            break;
                    }
                }
                if (degree != 0) {
                    //이미지가 돌려져 있을 경우 회전후 가져옴

                    Matrix matrix = new Matrix();
                    matrix.setRotate(degree);

                    Bitmap img = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl), null, option);
                    Bitmap imgRotated = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);

                    img.recycle();  //아웃오브 메모리 방지
                    img = imgRotated;
                    imageView.setImageBitmap(img);

                } else {
                    //그냥 가져온다.
                    Bitmap img = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl), null, option);
                    imageView.setImageBitmap(img);

                }


            } catch (Exception e) {

                e.printStackTrace();

            }

        }


    }

    private String findPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};

        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(), uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String path = cursor.getString(column_index);

        cursor.close();

        return path;


    }

    @Override
    protected void onResume() {
        super.onResume();


        if (count == 0) {
            Intent getIntent = getIntent();

            pageChaged = getIntent.getBooleanExtra("page", false);

            count = 1;
        } else {
            pageChaged = false;
        }


        if (!pageChaged) {

            SharedPreferences pref = getSharedPreferences("password", MODE_PRIVATE);
            if (pref.getBoolean("locked", false)) {

                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("activity", MainActivity.class);
                startActivity(intent);
                finish();


            }

        }

    }

    @Override
    public void onBackPressed() {
        if (successData == 1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("page", true);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        } else {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("page", true);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }
    }
}
