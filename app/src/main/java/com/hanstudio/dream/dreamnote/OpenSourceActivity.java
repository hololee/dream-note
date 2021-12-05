package com.hanstudio.dream.dreamnote;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OpenSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);



        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);
        TextView text4 = (TextView) findViewById(R.id.text4);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");
        text1.setTypeface(typeface);
        text2.setTypeface(typeface);
        text3.setTypeface(typeface);
        text4.setTypeface(typeface);



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
