package com.example.nanark.advance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nanark on 11/29/16.
 */
public class Outcome {
    private String Id;
    private String Date;
    private int Amounth;
    private String Description;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getAmounth() {
        return Amounth;
    }

    public void setAmounth(int amounth) {
        Amounth = amounth;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public static Cursor listOutcome(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TMPTB_EXPENSES, null);
        return cursor;
    }

    public static int totalOutcome(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM("+DatabaseHelper.COL_AMOUNTH+") FROM " + DatabaseHelper.TMPTB_EXPENSES, null);
        if(cursor.moveToFirst())
            return cursor.getInt(0);
        else
            return 0;
    }

    public static boolean saveOutcome(DatabaseHelper databaseHelper, Outcome outcome) {
        boolean status = false;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_DATE, outcome.getDate());
        contentValues.put(DatabaseHelper.COL_DESCRIPTION, outcome.getDescription());
        contentValues.put(DatabaseHelper.COL_AMOUNTH, outcome.getAmounth());
        long result = db.insert(DatabaseHelper.TMPTB_EXPENSES, null, contentValues);
        return result != -1;
    }

    public static boolean updateOutcome(DatabaseHelper databaseHelper, Outcome outcome) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_ID, outcome.getId());
        contentValues.put(DatabaseHelper.COL_DATE, outcome.getDate());
        contentValues.put(DatabaseHelper.COL_DESCRIPTION, outcome.getDescription());
        contentValues.put(DatabaseHelper.COL_AMOUNTH, outcome.getAmounth());
        long result = db.update(DatabaseHelper.TMPTB_EXPENSES, contentValues, DatabaseHelper.COL_ID+" = ? ", new String[]{outcome.getId()});
        return result != -1;
    }

    public static boolean deleteOutcome(DatabaseHelper databaseHelper, String Id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(DatabaseHelper.TMPTB_EXPENSES, DatabaseHelper.COL_ID+" = ? ", new String[]{Id});
        return result != -1;
    }
}

