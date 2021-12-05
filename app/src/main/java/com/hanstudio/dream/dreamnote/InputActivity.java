package com.hanstudio.dream.dreamnote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class InputActivity extends Activity {


    String password;

    Class activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "notosans-regular.ttf");
        Intent intent = getIntent();
        //이거다
        //이제 매인 수정
        activity= (Class) intent.getExtras().get("activity");


        final EditText editText = (EditText) findViewById(R.id.pass);
        editText.setTypeface(typeface);

        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        manager.showSoftInputFromInputMethod(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED);

        SharedPreferences pref = getSharedPreferences("password", MODE_PRIVATE);
        Boolean pass = pref.getBoolean("locked", false);

        password = pref.getString("pass", "null");

        if (pass == true) {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                    final boolean isEnterEvent = (event != null) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);

                    if (actionId == EditorInfo.IME_ACTION_DONE || isEnterEvent) {

                        if (password.equals(editText.getText().toString().trim())) {

                            Intent intent = new Intent(getApplicationContext(),activity);
                            intent.putExtra("page", true);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0,0);


                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT);
                            toast.show();
                        }


                    }

                    return true;
                }
            });

        }


    }


    @Override
    public void onBackPressed() {


    }
}
