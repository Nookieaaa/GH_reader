package com.nookdev.githubreader.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nookdev.githubreader.Models.Profile;

public class Database {

    private SQLiteDatabase database;
    private DBOpenHelper dbOpenHelper;

    public Database(Context context){
        dbOpenHelper = new DBOpenHelper(context);
        database = dbOpenHelper.getWritableDatabase();
    }

    public long add(String userName, String company, int followers, int following, String adress){
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.COLUMN_USERNAME,userName);
        cv.put(DBOpenHelper.COLUMN_COMPANY,company);
        cv.put(DBOpenHelper.COLUMN_FOLLOWERS, followers);
        cv.put(DBOpenHelper.COLUMN_FOLLOWING, following);
        cv.put(DBOpenHelper.COLUMN_ADRESS, adress);

        if (inBase(userName)){
            return -1;
        }

        database.beginTransaction();

        long _id = -1;

        try {
            _id = database.insert(DBOpenHelper.TABLE_USERS, null, cv);
            database.setTransactionSuccessful();
        }
        finally {
            database.endTransaction();
        }
        return _id;
    }

    public long add(Profile profile) {
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.COLUMN_USERNAME,profile.username);
        cv.put(DBOpenHelper.COLUMN_COMPANY,profile.company);
        cv.put(DBOpenHelper.COLUMN_FOLLOWERS, profile.followers);
        cv.put(DBOpenHelper.COLUMN_FOLLOWING, profile.following);
        cv.put(DBOpenHelper.COLUMN_ADRESS, profile.html_adress.toString());

        if (inBase(profile.username)){
            return -1;
        }

        database.beginTransaction();

        long _id = -1;

        try {
            _id = database.insert(DBOpenHelper.TABLE_USERS, null, cv);
            database.setTransactionSuccessful();
        }
        finally {
            database.endTransaction();
        }
        return _id;
    }

    private boolean inBase(String userName) {
        Cursor c = database.query(DBOpenHelper.TABLE_USERS,
                null,
                DBOpenHelper.COLUMN_USERNAME+" =? ",
                new String[] {userName},
                null,
                null,
                null);

        return c.getCount()!=0;
    }

    private void update(){

    }


    public Profile findProfile(String username){

        String[] param = new String[]{username};
        Cursor c = database.query(DBOpenHelper.TABLE_USERS,
                null,
                DBOpenHelper.COLUMN_USERNAME+" =? ",
                param,
                null,
                null,
                null);

        if (c.getCount()!=0){
            c.moveToFirst();
            int col_username = c.getColumnIndex(DBOpenHelper.COLUMN_USERNAME);
            int col_company = c.getColumnIndex(DBOpenHelper.COLUMN_COMPANY);
            int col_adress = c.getColumnIndex(DBOpenHelper.COLUMN_ADRESS);
            int col_followers = c.getColumnIndex(DBOpenHelper.COLUMN_FOLLOWERS);
            int col_following = c.getColumnIndex(DBOpenHelper.COLUMN_FOLLOWING);

            String c_username = c.getString(col_username);
            String c_company = c.getString(col_company);
            String c_adress = c.getString(col_adress);
            int c_followers = c.getInt(col_followers);
            int c_following = c.getInt(col_following);

            return new Profile(c_username,c_company,c_followers,c_following,null,c_adress);
        }
        else
            return null;

    }

    public void close(){
        if (database!=null)
            if(database.isOpen())
                database.close();
    }



}
