package com.nookdev.githubreader.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nookdev.githubreader.Models.Profile;

public class Database {

    private SQLiteDatabase database;
    private DBOpenHelper dbOpenHelper;

    public static final int DB_STATUS_ADDED = 1;
    public static final int DB_STATUS_UPDATED = 2;
    public static final int DB_STATUS_ERROR = 3;
    public static final int DB_STATUS_EXISTS = 4;

    public Database(Context context){
        dbOpenHelper = new DBOpenHelper(context);
        database = dbOpenHelper.getWritableDatabase();
    }

    public int add(String userName, String company, int followers, int following, String adress){
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.COLUMN_USERS_USERNAME,userName);
        cv.put(DBOpenHelper.COLUMN_USERS_COMPANY,company);
        cv.put(DBOpenHelper.COLUMN_USERS_FOLLOWERS, followers);
        cv.put(DBOpenHelper.COLUMN_USERS_FOLLOWING, following);
        cv.put(DBOpenHelper.COLUMN_USERS_ADRESS, adress);

        int status;

        if (inBase(userName)){
            status = DB_STATUS_EXISTS;
        }

        database.beginTransaction();

        long _id;

        try {
            _id = database.insert(DBOpenHelper.TABLE_USERS, null, cv);
            database.setTransactionSuccessful();
            status = DB_STATUS_ADDED;
        }
        finally {
            database.endTransaction();
        }
        if (_id>0)
            return DB_STATUS_ADDED;

        return DB_STATUS_ERROR;
    }

    public int add(Profile profile) {
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.COLUMN_USERS_USERNAME,profile.username);
        cv.put(DBOpenHelper.COLUMN_USERS_COMPANY,profile.company);
        cv.put(DBOpenHelper.COLUMN_USERS_FOLLOWERS, profile.followers);
        cv.put(DBOpenHelper.COLUMN_USERS_FOLLOWING, profile.following);
        cv.put(DBOpenHelper.COLUMN_USERS_ADRESS, profile.html_adress.toString());

        int status;

        if (inBase(profile.username)){
            status = update(profile);
            return status;
        }

        database.beginTransaction();

        long _id;

        try {
            _id = database.insert(DBOpenHelper.TABLE_USERS, null, cv);
            database.setTransactionSuccessful();
            status = DB_STATUS_ADDED;
        }
        finally {
            database.endTransaction();
        }
        if (_id<0)
            status = DB_STATUS_ERROR;

        return status;
    }

    private boolean inBase(String userName) {
        Cursor c = database.query(DBOpenHelper.TABLE_USERS,
                null,
                DBOpenHelper.COLUMN_USERS_USERNAME +" =? ",
                new String[] {userName},
                null,
                null,
                null);

        return c.getCount()!=0;
    }

    private int update(Profile profile){
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.COLUMN_USERS_USERNAME,profile.username);
        cv.put(DBOpenHelper.COLUMN_USERS_COMPANY,profile.company);
        cv.put(DBOpenHelper.COLUMN_USERS_FOLLOWERS, profile.followers);
        cv.put(DBOpenHelper.COLUMN_USERS_FOLLOWING, profile.following);
        cv.put(DBOpenHelper.COLUMN_USERS_ADRESS, profile.html_adress.toString());

        int status;

        database.beginTransaction();
        try {
            database.update(DBOpenHelper.TABLE_USERS,
                    cv,
                    DBOpenHelper.COLUMN_USERS_USERNAME + " =? ",
                    new String[]{profile.username});
            database.setTransactionSuccessful();
            status = DB_STATUS_UPDATED;
        }
        catch (Error e){
            status = DB_STATUS_ERROR;
        }
        finally{
            database.endTransaction();
        }

        return status;
    }


    public Profile findProfile(String username){

        String[] param = new String[]{username};
        Cursor c = database.query(DBOpenHelper.TABLE_USERS,
                null,
                DBOpenHelper.COLUMN_USERS_USERNAME +" =? ",
                param,
                null,
                null,
                null);

        if (c.getCount()!=0){
            c.moveToFirst();
            int col_username = c.getColumnIndex(DBOpenHelper.COLUMN_USERS_USERNAME);
            int col_company = c.getColumnIndex(DBOpenHelper.COLUMN_USERS_COMPANY);
            int col_adress = c.getColumnIndex(DBOpenHelper.COLUMN_USERS_ADRESS);
            int col_followers = c.getColumnIndex(DBOpenHelper.COLUMN_USERS_FOLLOWERS);
            int col_following = c.getColumnIndex(DBOpenHelper.COLUMN_USERS_FOLLOWING);

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
