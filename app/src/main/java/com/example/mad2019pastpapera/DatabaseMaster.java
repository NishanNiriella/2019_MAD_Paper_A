package com.example.mad2019pastpapera;

import android.provider.BaseColumns;

public class DatabaseMaster {
    public DatabaseMaster(){

    }

    public class Users implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "userName";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_USERTYPE = "userType";
    }

    public class Movies implements BaseColumns{
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_MOVIENAME = "movieName";
        public static final String COLUMN_NAME_YEAR = "movieYear";
    }

    public class Comments implements BaseColumns{
        public static final String TABLE_NAME = "comments";
        public static final String COLUMN_NAME_MOVIENAME = "movieName";
        public static final String COLUMN_NAME_MOVIERATING = "movieRating";
        public static final String COLUMN_NAME_MOVIECOMMENTS = "movieComments";
    }
}
