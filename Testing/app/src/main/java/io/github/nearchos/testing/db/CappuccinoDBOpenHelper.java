package io.github.nearchos.testing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Nearchos
 * Created: 29-Mar-18
 */
public class CappuccinoDBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "cappuccino.db";
    public static final int VERSION = 1;

    public CappuccinoDBOpenHelper(final Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create default tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // todo
    }
}