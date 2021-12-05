package com.hanstudio.dream.dreamnote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by L on 2015-12-10.
 */
public class howToActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how);

        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);
        TextView text4 = (TextView) findViewById(R.id.text4);
        TextView text5 = (TextView) findViewById(R.id.text5);
        TextView text6 = (TextView) findViewById(R.id.text6);
        TextView text7 = (TextView) findViewById(R.id.text7);
        TextView text8 = (TextView) findViewById(R.id.text8);
        TextView text9 = (TextView) findViewById(R.id.text9);
        TextView text10 = (TextView) findViewById(R.id.text10);
        TextView text11 = (TextView) findViewById(R.id.text11);
        TextView text12 = (TextView) findViewById(R.id.text12);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");
        text1.setTypeface(typeface);
        text2.setTypeface(typeface);
        text3.setTypeface(typeface);
        text4.setTypeface(typeface);
        text5.setTypeface(typeface);
        text6.setTypeface(typeface);
        text7.setTypeface(typeface);
        text8.setTypeface(typeface);
        text9.setTypeface(typeface);
        text10.setTypeface(typeface);
        text11.setTypeface(typeface);
        text12.setTypeface(typeface);



    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), setActivity.class);
        intent.putExtra("page", true);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
