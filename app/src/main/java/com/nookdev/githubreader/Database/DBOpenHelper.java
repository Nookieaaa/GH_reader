package com.nookdev.githubreader.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBOpenHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USERS_USERNAME = "username";
    public static final String COLUMN_USERS_COMPANY = "company";
    public static final String COLUMN_USERS_FOLLOWERS = "followers";
    public static final String COLUMN_USERS_FOLLOWING = "following";
    public static final String COLUMN_USERS_ADRESS = "adress";

    private static final String DATABASE_NAME = "GitHubReader";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE "+ TABLE_USERS +"("+
            _ID+" integer primary key autoincrement, "+
            COLUMN_USERS_USERNAME + " TEXT not null, "+
            COLUMN_USERS_COMPANY + " TEXT, "+
            COLUMN_USERS_ADRESS + " TEXT, "+
            COLUMN_USERS_FOLLOWERS + " INTEGER, " +
            COLUMN_USERS_FOLLOWING + " INTEGER);";

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
