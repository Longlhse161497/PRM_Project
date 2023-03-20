package com.carsonskjerdal.app.groceryshop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private Integer mOpenCounter = 0;

    private static DatabaseManager sInstance;
    private DatabaseHelper myDbHelper;
    private SQLiteDatabase mDatabase;

    public DatabaseManager(Context context) {
        myDbHelper = new DatabaseHelper(context);
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter += 1;
        if (mOpenCounter == 1) {
            // Opening new database
            mDatabase = myDbHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter -= 1;
        if (mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }

    public Cursor queryAllItems(String table) {
        //Implements the query
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + table, null);
    }

    public void deleteAll(String table) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + table);
    }

    public void deleteById(String table, int id) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + table + " WHERE id = '" + id + "'");
    }

}
