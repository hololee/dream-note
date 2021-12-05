package com.hanstudio.dream.dreamnote;

import android.app.Activity;
import android.content.CursorLoader;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

/**
 * Created by L on 2015-12-04.
 */
public class addActivity extends Activity {

    final static String DATABASE_NAME = "dream";
    final static int VER = 1;
    final static String TABLE_NAME = "table1";

    EditText insertedText;

    ImageView imageView;


    int degree;

    int year;
    int month;
    int date;

    Uri uri;

    Boolean pageChaged;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);





        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        month += 1;
        date = calendar.get(Calendar.DATE);

        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setVisibility(View.INVISIBLE);


        TextView textview = (TextView) findViewById(R.id.textView3);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");

        textview.setTypeface(typeface);


        Button buttonOk = (Button) findViewById(R.id.button_ok);
        Button buttonImage = (Button) findViewById(R.id.button_image);


        insertedText = (EditText) findViewById(R.id.insert);
        insertedText.setTypeface(typeface);


        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage();

            }
        });


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();

            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();


        SharedPreferences pref = getSharedPreferences("content", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = pref.edit();
        prefEdit.putString("text", insertedText.getText().toString());
        prefEdit.commit();


    }


    @Override
    protected void onResume() {
        super.onResume();

        if(count== 0) {
            Intent getIntent = getIntent();

            pageChaged = getIntent.getBooleanExtra("page", false);

            count = 1;
        }else if(count == 3){
            pageChaged = true;
            count = 1;
        }else{
            pageChaged = false;
        }


        if(!pageChaged) {

            SharedPreferences pref = getSharedPreferences("password", MODE_PRIVATE);
            if (pref.getBoolean("locked", false)) {

                Intent intent = new Intent(getApplicationContext(),InputActivity.class);
                intent.putExtra("activity",addActivity.class);
                startActivity(intent);
                finish();


            }

        }

        SharedPreferences pref = getSharedPreferences("content", MODE_PRIVATE);
        String content = pref.getString("text", "");
        insertedText.setText(content);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences pref = getSharedPreferences("content", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = pref.edit();
        prefEdit.putString("text", "");
        prefEdit.commit();


    }


    private void saveData() {


        databaseHelper helper = new databaseHelper(getApplicationContext(), DATABASE_NAME, null, VER);
        SQLiteDatabase database = helper.getWritableDatabase();

        if (insertedText.length() == 0) {

            Toast toast = Toast.makeText(getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT);
            toast.show();

        } else {

            if (uri == null) {
                database.execSQL("insert into " + TABLE_NAME + "(success, content, year, month, date, count, imageurl) values (0, '" + insertedText.getText().toString() + "', " + year + ", " + month + ", " + date + ", 0, 'null')");
                database.close();

            } else {
                database.execSQL("insert into " + TABLE_NAME + "(success, content, year, month, date, count, imageurl) values (0, '" + insertedText.getText().toString() + "', " + year + ", " + month + ", " + date + ", 0, '" + uri.toString() + "')");
                database.close();

            }

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("page", true);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }

    }

    private void loadImage() {

        count =3;

        Intent imageSelectIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageSelectIntent, 1);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {


            //이미지 보이기
            imageView.setVisibility(View.VISIBLE);


            uri = data.getData();

            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 2;


            String path = findPath(uri);


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

                    Bitmap img = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, option);
                    Bitmap imgRotated = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);

                    img.recycle();  //아웃오브 메모리 방지
                    img = imgRotated;
                    imageView.setImageBitmap(img);

                } else {
                    //그냥 가져온다.
                    Bitmap img = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, option);
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
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("page", true);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
