package com.nookdev.githubreader.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBOpenHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final String TABLE_USERS = "Users";


    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_FOLLOWERS = "followers";
    public static final String COLUMN_FOLLOWING = "following";
    public static final String COLUMN_ADRESS = "adress";

    private static final String DATABASE_NAME = "GitHubReader";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE "+ TABLE_USERS +"("+
            _ID+" integer primary key autoincrement, "+
            COLUMN_USERNAME + " TEXT not null, "+
            COLUMN_COMPANY + " TEXT, "+
            COLUMN_ADRESS + " TEXT, "+
            COLUMN_FOLLOWERS + " INTEGER, " +
            COLUMN_FOLLOWING + " INTEGER);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
