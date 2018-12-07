package com.example.rkjc.news_app_2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.rkjc.news_app_2.Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR;
import static com.example.rkjc.news_app_2.Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION;
import static com.example.rkjc.news_app_2.Contract.TABLE_NEWS.COLUMN_NAME_DATE;
import static com.example.rkjc.news_app_2.Contract.TABLE_NEWS.COLUMN_NAME_TITLE;
import static com.example.rkjc.news_app_2.Contract.TABLE_NEWS.COLUMN_NAME_URL;
import static com.example.rkjc.news_app_2.Contract.TABLE_NEWS.COLUMN_NAME_URLIMG;
import static com.example.rkjc.news_app_2.Contract.TABLE_NEWS.TABLE_NAME;

public class DBUtils {
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_NAME, null, null
                , null, null, null, COLUMN_NAME_DATE + "desc");
        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> items){
        db.beginTransaction();
        try{
            for (NewsItem item:items){
                ContentValues contentValues=new ContentValues();
                contentValues.put(COLUMN_NAME_AUTHOR,item.getAuthor());
                contentValues.put(COLUMN_NAME_TITLE,item.getTitle());
                contentValues.put(COLUMN_NAME_DESCRIPTION,item.getDescription());
                contentValues.put(COLUMN_NAME_DATE,item.getDate());
                contentValues.put(COLUMN_NAME_URL,item.getUrl());
                contentValues.put(COLUMN_NAME_URLIMG,item.getUrlimg());
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }
    public static void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME,null,null);
    }
}