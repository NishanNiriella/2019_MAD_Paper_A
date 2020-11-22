package com.example.mad2019pastpapera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "Movies.db";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USERS =
                "CREATE TABLE "+ DatabaseMaster.Users.TABLE_NAME + " (" +
                        DatabaseMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        DatabaseMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                        DatabaseMaster.Users.COLUMN_NAME_PASSWORD + " TEXT," +
                        DatabaseMaster.Users.COLUMN_NAME_USERTYPE + " TEXT)";
        db.execSQL(SQL_CREATE_USERS);

        String SQL_CREATE_MOVIES =
                "CREATE TABLE "+ DatabaseMaster.Movies.TABLE_NAME + " (" +
                        DatabaseMaster.Movies._ID + " INTEGER PRIMARY KEY," +
                        DatabaseMaster.Movies.COLUMN_NAME_MOVIENAME + " TEXT," +
                        DatabaseMaster.Movies.COLUMN_NAME_YEAR + " INTEGER)";
        db.execSQL(SQL_CREATE_MOVIES);

        String SQL_CREATE_COMMENTS =
                "CREATE TABLE " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                        DatabaseMaster.Comments._ID + " INTEGER PRIMARY KEY," +
                        DatabaseMaster.Comments.COLUMN_NAME_MOVIENAME + " TEXT," +
                        DatabaseMaster.Comments.COLUMN_NAME_MOVIERATING + " INTEGER," +
                        DatabaseMaster.Comments.COLUMN_NAME_MOVIECOMMENTS + " TEXT)";
        db.execSQL(SQL_CREATE_COMMENTS);
    }

    public long registerUser(String userName, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(DatabaseMaster.Users.COLUMN_NAME_PASSWORD, password);

        return db.insert(DatabaseMaster.Users.TABLE_NAME,null,values);
    }

    public int loginUser(String username, String password){
        String pwd = null;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + DatabaseMaster.Users.COLUMN_NAME_PASSWORD + " FROM " + DatabaseMaster.Users.TABLE_NAME +
                " WHERE " + DatabaseMaster.Users.COLUMN_NAME_USERNAME + "='" + username + "'";

        Cursor cursor = db.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            pwd = cursor.getString(0);
        }

        System.out.println(pwd);

        if(pwd.equals(password)){
            if(username.equals("admin")){
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }

    }

    public long addMovies(String movieName, int year){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Movies.COLUMN_NAME_MOVIENAME, movieName);
        values.put(DatabaseMaster.Movies.COLUMN_NAME_YEAR, year);

        return db.insert(DatabaseMaster.Movies.TABLE_NAME, null, values);
    }

    public ArrayList viewMovies(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Movies.COLUMN_NAME_MOVIENAME,
                DatabaseMaster.Movies.COLUMN_NAME_YEAR
        };

        Cursor cursor = db.query(
                DatabaseMaster.Movies.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                DatabaseMaster.Movies.COLUMN_NAME_MOVIENAME + " ASC"
        );

        ArrayList movieData = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(1);
                movieData.add(name);
            } while(cursor.moveToNext());
        }

        return movieData;
    }

    public long insertComments(String movieName, String comment, int rating){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Comments.COLUMN_NAME_MOVIENAME, movieName);
        values.put(DatabaseMaster.Comments.COLUMN_NAME_MOVIECOMMENTS, comment);
        values.put(DatabaseMaster.Comments.COLUMN_NAME_MOVIERATING, rating);

        return db.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);
    }

    public ArrayList viewComments(String movieName){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Comments.COLUMN_NAME_MOVIECOMMENTS,
                DatabaseMaster.Comments.COLUMN_NAME_MOVIERATING,
                DatabaseMaster.Comments.COLUMN_NAME_MOVIENAME
        };

        String selection = DatabaseMaster.Comments.COLUMN_NAME_MOVIENAME + " LIKE ?";
        String[] selectionArgs = {movieName};

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
                );

        ArrayList commentList = new ArrayList();

        if(cursor.moveToFirst()){
            do{
                commentList.add(cursor.getString(1));
            } while(cursor.moveToNext());
        }

        return commentList;
    }

    public int getTotalRating(String movName){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT SUM(" + DatabaseMaster.Comments.COLUMN_NAME_MOVIERATING + ") AS TotalRate FROM " + DatabaseMaster.Comments.TABLE_NAME +
                " WHERE " + DatabaseMaster.Comments.COLUMN_NAME_MOVIENAME + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{movName});

        int tot;

        if(cursor.moveToFirst()){
            //System.out.println("cursor" + Integer.parseInt(cursor.getString(0)));
            if(cursor.getString(0) == null){
                return 0;
            } else {
                tot = Integer.parseInt(cursor.getString(0));
                return tot;
            }
        } else{
            System.out.println("No data");
            return 0;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
