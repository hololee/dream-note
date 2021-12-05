package com.hanstudio.dream.dreamnote;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by L on 2015-12-04.
 */
public class ListItemView extends RelativeLayout {

    Context mContext;
    TextView contentMain;
    TextView fromDate;
    ImageView back;

    public ListItemView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);

    }


    public void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "notosans-regular.ttf");

        contentMain = (TextView) findViewById(R.id.content_main);
        fromDate = (TextView) findViewById(R.id.from);
        back = (ImageView) findViewById(R.id.interest);

        contentMain.setTypeface(typeface);
        fromDate.setTypeface(typeface);

    }

    public void setFromDate(int year, int month, int date) {

        String text = "FROM : " + year + "-" + month + "-" + date;
        fromDate.setText(text);

    }

    public void setContent(String content) {

        contentMain.setText(content);

    }

    public void setColor(int count) {


        if (Build.VERSION.SDK_INT >= 23) {

            if (count > 640) {//빨
              back.getBackground().setColorFilter(getResources().getColor(R.color.C8,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);
            } else if (count > 320) {//주
                back.getBackground().setColorFilter(getResources().getColor(R.color.C7,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);

            } else if (count > 160) {//노
                back.getBackground().setColorFilter(getResources().getColor(R.color.C6,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);
            } else if (count > 80) {//초
                back.getBackground().setColorFilter(getResources().getColor(R.color.C5,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);

            } else if (count > 40) {//파
                back.getBackground().setColorFilter(getResources().getColor(R.color.C4,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);

            } else if (count > 20) {//남
                back.getBackground().setColorFilter(getResources().getColor(R.color.C3,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);

            } else if (count > 10) {//보
                back.getBackground().setColorFilter(getResources().getColor(R.color.C2,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);

            } else { // 미약 회
                back.getBackground().setColorFilter(getResources().getColor(R.color.C1,mContext.getTheme()), PorterDuff.Mode.MULTIPLY);

            }




        } else {

            if (count > 640) {//빨
                back.getBackground().setColorFilter(getResources().getColor(R.color.C8), PorterDuff.Mode.MULTIPLY);
            } else if (count > 320) {//주
                back.getBackground().setColorFilter(getResources().getColor(R.color.C7), PorterDuff.Mode.MULTIPLY);

            } else if (count > 160) {//노
                back.getBackground().setColorFilter(getResources().getColor(R.color.C6), PorterDuff.Mode.MULTIPLY);
            } else if (count > 80) {//초
                back.getBackground().setColorFilter(getResources().getColor(R.color.C5), PorterDuff.Mode.MULTIPLY);

            } else if (count > 40) {//파
                back.getBackground().setColorFilter(getResources().getColor(R.color.C4), PorterDuff.Mode.MULTIPLY);

            } else if (count > 20) {//남
                back.getBackground().setColorFilter(getResources().getColor(R.color.C3), PorterDuff.Mode.MULTIPLY);

            } else if (count > 10) {//보
                back.getBackground().setColorFilter(getResources().getColor(R.color.C2), PorterDuff.Mode.MULTIPLY);

            } else { // 미약 회
                back.getBackground().setColorFilter(getResources().getColor(R.color.C1), PorterDuff.Mode.MULTIPLY);

            }

        }
    }

}
