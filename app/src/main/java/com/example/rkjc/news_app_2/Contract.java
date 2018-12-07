package com.example.rkjc.news_app_2;

import android.provider.BaseColumns;

public class Contract {
    public static class TABLE_NEWS implements BaseColumns{
        public static final String TABLE_NAME="news_item";
        public static final String COLUMN_NAME_AUTHOR="author";
        public static final String COLUMN_NAME_DESCRIPTION="desc";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_URL="url";
        public static final String COLUMN_NAME_DATE="date";
        public static final String COLUMN_NAME_URLIMG="urlimg";
    }
}
