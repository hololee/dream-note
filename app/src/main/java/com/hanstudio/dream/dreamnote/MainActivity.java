package com.hanstudio.dream.dreamnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;

public class MainActivity extends Activity implements CaulyCloseAdListener {


    private static final String APP_CODE = "D8jIRd8w";
    CaulyCloseAd mCloseAd;


    final static String DATABASE_NAME = "dream";
    final static int VER = 1;
    final static String TABLE_NAME = "table1";


    Boolean pageChaged;

    ListView listView, listViews;
    TextView text;

    LoadText tt;

    Boolean pageChaged2;
    int countsa = 0;


    FloatingActionButton fab;


    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViews = (ListView) findViewById(R.id.listViews);


        Button button = (Button) findViewById(R.id.set);
        button.setOnClickListener(ButtonSetClickListner);



        listViews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView text = (TextView) view.findViewById(R.id.content_main);
                final String content = text.getText().toString();

                AlertDialog.Builder dial = new AlertDialog.Builder(MainActivity.this);
                dial.setMessage("삭제 하시겠습니까?");
                dial.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper helper = new databaseHelper(getApplicationContext(), DATABASE_NAME, null, VER);
                        SQLiteDatabase db = helper.getReadableDatabase();

                        db.execSQL("delete from " + TABLE_NAME + " where content == " + "'" + content + "'");

                        db.close();
                        invalidates();
                    }
                });
                dial.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                dial.show();

                return true;
            }
        });


        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView text = (TextView) view.findViewById(R.id.content_main);
                String content = text.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ItemViewActivity.class);
                intent.putExtra("page", true);
                intent.putExtra("content", content);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder(APP_CODE).build();
        mCloseAd = new CaulyCloseAd();
        mCloseAd = new CaulyCloseAd();

        mCloseAd.setAdInfo(closeAdInfo);
        mCloseAd.setCloseAdListener(this);

        mCloseAd.disableBackKey();


        TextView todayTime = (TextView) findViewById(R.id.textView);
        text = (TextView) findViewById(R.id.textView2);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");
        todayTime.setTypeface(typeface);
        text.setTypeface(typeface);


        listView = (ListView) findViewById(R.id.listView);

//floating 설정

        // fab.setType(FloatingActionButton.TYPE_MINI);
        //api 21+
        // fab.setColorRipple(getResources().getColor(R.color.ripple));

        fab.attachToListView(listView);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month += 1;
        int date = calendar.get(Calendar.DATE);


        todayTime.setText(year + "년 " + month + "월 " + date + "일");

        tt = new LoadText();
        text.setText(tt.load());


        fab.setOnClickListener(ButtonAddClickListener);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeText();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView text = (TextView) view.findViewById(R.id.content_main);
                final String content = text.getText().toString();

                AlertDialog.Builder dial = new AlertDialog.Builder(MainActivity.this);
                dial.setMessage("삭제 하시겠습니까?");
                dial.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper helper = new databaseHelper(getApplicationContext(), DATABASE_NAME, null, VER);
                        SQLiteDatabase db = helper.getReadableDatabase();

                        db.execSQL("delete from " + TABLE_NAME + " where content == " + "'" + content + "'");

                        db.close();
                        invalidate();
                    }
                });
                dial.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                dial.show();

                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView text = (TextView) view.findViewById(R.id.content_main);
                String content = text.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ItemViewActivity.class);
                intent.putExtra("page", true);
                intent.putExtra("content", content);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();

            }
        });


    }


    View.OnClickListener ButtonSetClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(), setActivity.class);
            intent.putExtra("page", true);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    };


    View.OnClickListener ButtonAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent intent = new Intent(getApplicationContext(), addActivity.class);
            intent.putExtra("page", true);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    };


    @Override
    protected void onResume() {
        super.onResume();


        if (countsa == 0) {
            Intent getIntent = getIntent();

            pageChaged2 = getIntent.getBooleanExtra("page", false);

            countsa = 1;
        } else {
            pageChaged2 = false;
        }


        if (!pageChaged2) {

            SharedPreferences pref = getSharedPreferences("password", MODE_PRIVATE);
            if (pref.getBoolean("locked", false)) {

                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("activity", MainActivity.class);
                startActivity(intent);
                finish();


            }
        }


        invalidates();


        if (mCloseAd != null) {
            mCloseAd.resume(this); // 필수 호출
        }


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


        invalidate();


    }

    public void invalidates() {

        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext());

        databaseHelper helper = new databaseHelper(getApplicationContext(), DATABASE_NAME, null, VER);
        SQLiteDatabase db = helper.getReadableDatabase();

        String args[] = {"1"};

        Cursor cursor = db.rawQuery("select success, content, year, month, date, count from " + TABLE_NAME + " where success == ?", args);
        //                                      0           1      2       3       4      5




        int totalItem = cursor.getCount();

        for (int i = 0; i < totalItem; i++) {

            cursor.moveToNext();
            item item = new item();
            item.setContent(cursor.getString(1));
            item.setYear(cursor.getInt(2));
            item.setMonth(cursor.getInt(3));
            item.setDate(cursor.getInt(4));
            item.setCount(cursor.getInt(5));

            adapter.addItem(item);

        }

        helper.close();
        cursor.close();
        db.close();

        listViews.setAdapter(adapter);


    }


    public void invalidate() {

        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext());

        databaseHelper helper = new databaseHelper(getApplicationContext(), DATABASE_NAME, null, VER);
        SQLiteDatabase db = helper.getReadableDatabase();

        String args[] = {"0"};
        Cursor cursor = db.rawQuery("select success, content, year, month, date, count from " + TABLE_NAME + " where success == ?", args);
        //                                      0           1      2       3       4      5
        int totalItem = cursor.getCount();

        for (int i = 0; i < totalItem; i++) {

            cursor.moveToNext();
            item item = new item();
            item.setContent(cursor.getString(1));
            item.setYear(cursor.getInt(2));
            item.setMonth(cursor.getInt(3));
            item.setDate(cursor.getInt(4));
            item.setCount(cursor.getInt(5));

            adapter.addItem(item);

        }

        helper.close();
        cursor.close();
        db.close();

        listView.setAdapter(adapter);


    }


    public void changeText() {


        text.setText(tt.load());


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
// 앱을 처음 설치하여 실행할 때, 필요한 리소스를 다운받았는지 여부.
            if (mCloseAd.isModuleLoaded()) {
                mCloseAd.show(this);
            } else {
// 광고에 필요한 리소스를 한번만 다운받는데 실패했을 때 앱의 종료팝업 구현
                showDefaultClosePopup();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void showDefaultClosePopup() {
        new AlertDialog.Builder(this).setTitle("").setMessage("종료 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("아니요", null)
                .show();
    }

    @Override
    public void onReceiveCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onShowedCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onFailedToReceiveCloseAd(CaulyCloseAd caulyCloseAd, int i, String s) {

    }

    @Override
    public void onLeftClicked(CaulyCloseAd caulyCloseAd) {

    }

    @Override
    public void onRightClicked(CaulyCloseAd caulyCloseAd) {
        finish();
    }

    @Override
    public void onLeaveCloseAd(CaulyCloseAd caulyCloseAd) {

    }
}
