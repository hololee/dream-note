package com.hanstudio.dream.dreamnote;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by L on 2015-12-04.
 */
public class databaseHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "table1";

    public databaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public databaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table if not exists " + TABLE_NAME + "(_id integer PRIMARY KEY autoincrement, " +
                "success integer, content text, year integer, month integer, date integer, count integer, imageurl text)");


        //0은 false 1은 true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
