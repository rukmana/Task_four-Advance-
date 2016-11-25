package com.example.nanark.advance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nanark on 11/25/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Expenses.db";

    public static final String TABLE_NAME = "expenses";

    public static final String COL_1 = "DESCRIPTION";

    public static final String COL_2 = "AMOUNT";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +

            COL_1 + " TEXT PRIMARY KEY AUTOINCREMENT, " +

            COL_2 + " INTEGER );";



    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }



    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }



    public boolean save_expenses(String expenses) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content_values = new ContentValues();

        content_values.put(COL_2, expenses);

        long result = db.insert(TABLE_NAME, null, content_values);

        return result != -1;

    }



    public Cursor list_expenses() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor students = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return students;

    }



    public boolean synchronize(String description, String amount) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content_values = new ContentValues();

        content_values.put(COL_1, description);

        content_values.put(COL_2, amount);

        db.update(TABLE_NAME, content_values, "DESCRIPTION = ? ", new String[]{description});

        return true;

    }


}