package com.hanstudio.dream.dreamnote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 2015-12-04.
 */
public class ListViewAdapter extends BaseAdapter {


    Context mContext;


    List<item> items = new ArrayList<>();

    public void addItem(item item) {

        items.add(item);

    }

    public ListViewAdapter(Context context) {

        mContext = context;


    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ListItemView view;


        if (convertView == null) {

            view = new ListItemView(mContext);


        } else {
            view = (ListItemView) convertView;
        }

        String content = items.get(position).getContent();
        int year = items.get(position).getYear();
        int month = items.get(position).getMonth();
        int date = items.get(position).getDate();
        int count = items.get(position).getCount();

        view.setContent(content);
        view.setColor(count);
        view.setFromDate(year, month, date);


        return view;
    }
}
