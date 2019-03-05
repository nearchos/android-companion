package io.github.nearchos.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "entries.db";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_BLOG_ENTRIES); // create DB schema
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // in our case, we simply delete all data and recreate the DB
        sqLiteDatabase.execSQL(SQL_DELETE_BLOG_ENTRIES);
        onCreate(sqLiteDatabase); // force recreate DB schema
    }

    private static final String SQL_CREATE_BLOG_ENTRIES =
            "CREATE TABLE entries (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "title TEXT NOT NULL, "+
                    "body TEXT NOT NULL "+
                    ")";

    private static final String SQL_DELETE_BLOG_ENTRIES =
            "DROP TABLE IF EXISTS entries";
}