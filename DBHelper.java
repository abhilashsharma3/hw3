package com.example.rkjc.news_app_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static final String DB_NAME="news_item.db";
    private static final String TAG="dbhelper";

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE"+Contract.TABLE_NEWS.TABLE_NAME+"("+
                Contract.TABLE_NEWS._ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR+"TEXT,"+
                Contract.TABLE_NEWS.COLUMN_NAME_TITLE+"TEXT,"+
                Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION+"TEXT,"+
                Contract.TABLE_NEWS.COLUMN_NAME_URL+"TEXT,"+
                Contract.TABLE_NEWS.COLUMN_NAME_URLIMG+"TEXT"+
                Contract.TABLE_NEWS.COLUMN_NAME_DATE+"TEXT,"+");";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
