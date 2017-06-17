package com.example.notifythem.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akshay on 13/6/17.
 */

public class NotifyDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="notifythem.db";
    public static final int DATABASE_VERSION=1;


    public NotifyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ NotifyContract.NotifyEntry.TABLE_NAME+" ("+
                NotifyContract.NotifyEntry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_SENDERNAME+" TEXT,"+
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_TITLE+" TEXT,"+
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_CONTENT+" TEXT,"+
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_BRANCH+" INTEGER DEFAULT 1,"+
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_SECTION+" INTEGER DEFAULT 1,"+
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_YEAR+" INTEGER DEFAULT 1)"
        );

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ NotifyContract.NotifyEntry.TABLE_NAME);
        onCreate(db);
    }
}
