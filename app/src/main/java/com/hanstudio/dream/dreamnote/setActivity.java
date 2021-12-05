package com.hanstudio.dream.dreamnote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by L on 2015-12-04.
 */
public class setActivity extends Activity {


    Boolean pageChaged;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        TextView title = (TextView) findViewById(R.id.textView3);
        TextView how = (TextView) findViewById(R.id.how);
        TextView pass = (TextView) findViewById(R.id.pass);
        TextView open = (TextView) findViewById(R.id.open_source);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");

        title.setTypeface(typeface);
        how.setTypeface(typeface);
        pass.setTypeface(typeface);


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), OpenSourceActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();


            }
        });


        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), howToActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();

            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),passActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        if(count== 0) {
            Intent getIntent = getIntent();

            pageChaged = getIntent.getBooleanExtra("page", false);

            count = 1;
        }else{
            pageChaged = false;
        }


        if(!pageChaged) {

            SharedPreferences pref = getSharedPreferences("password", MODE_PRIVATE);
            if (pref.getBoolean("locked", false)) {

                Intent intent = new Intent(getApplicationContext(),InputActivity.class);
                intent.putExtra("activity",setActivity.class);
                startActivity(intent);
                finish();


            }

        }
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
