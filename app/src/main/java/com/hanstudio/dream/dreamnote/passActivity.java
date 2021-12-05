package com.hanstudio.dream.dreamnote;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by L on 2015-12-10.
 */
public class passActivity extends Activity {
    EditText edit1, edit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);


        TextView text1 = (TextView) findViewById(R.id.textView3);
        TextView text2 = (TextView) findViewById(R.id.text01);
        TextView text3 = (TextView) findViewById(R.id.text02);


        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");

        text1.setTypeface(typeface);
        text2.setTypeface(typeface);
        text3.setTypeface(typeface);
        edit1.setTypeface(typeface);
        edit2.setTypeface(typeface);


        Button button = (Button) findViewById(R.id.button_ok);
        Button delete = (Button) findViewById(R.id.button_delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("password", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();

                edit.putBoolean("locked", false);
                edit.commit();

                finish();
                overridePendingTransition(0, 0);



            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit1.getText().toString().trim().equals(edit2.getText().toString().trim())) {

                    SharedPreferences pref = getSharedPreferences("password", MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();

                    edit.putBoolean("locked", true);
                    edit.putString("pass", edit1.getText().toString());

                    edit.commit();

                    finish();
                    overridePendingTransition(0, 0);



                } else {

                    // Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT);
                    Toast toast = Toast.makeText(getApplicationContext(), ""+edit1.getText().toString() + ", " + edit2.getText().toString(), Toast.LENGTH_SHORT);
                    toast.show();

                }


            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(0,0);
    }
}
