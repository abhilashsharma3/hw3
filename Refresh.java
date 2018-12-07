package com.example.rkjc.news_app_2;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Refresh {
    static final String TAG = "Refresh";
    public static void refreshAction(Context context){
        ArrayList<NewsItem> news=null;
        URL url=NetworkUtils.buildURL();
        SQLiteDatabase db=new DBHelper(context).getWritableDatabase();
        try{
            DBUtils.deleteAll(db);
            String jstring=NetworkUtils.getResponseFromHttpUrl(url);
            news=JsonUtils.parseNews(jstring);
            DBUtils.bulkInsert(db,news);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        db.close();
    }
}
