package com.example.nanark.advance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nanark on 11/28/16.
 */
public class Expenses {
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

    public static Cursor listExpenses(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TMPTB_EXPENSES, null);
        return cursor;
    }

    public static int totalExpenses(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM("+DatabaseHelper.COL_AMOUNTH+") FROM " + DatabaseHelper.TMPTB_EXPENSES, null);
        if(cursor.moveToFirst())
            return cursor.getInt(0);
        else
            return 0;
    }

    public static boolean saveExpenses(DatabaseHelper databaseHelper, Expenses expenses) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_DATE, expenses.getDate());
        contentValues.put(DatabaseHelper.COL_DESCRIPTION, expenses.getDescription());
        contentValues.put(DatabaseHelper.COL_AMOUNTH, expenses.getAmounth());
        long result = db.insert(DatabaseHelper.TB_EXPENSES, null, contentValues);
        return result != -1;
    }

    public static boolean updateExpenses(DatabaseHelper databaseHelper, Expenses expenses) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_ID, expenses.getId());
        contentValues.put(DatabaseHelper.COL_DATE, expenses.getDate());
        contentValues.put(DatabaseHelper.COL_DESCRIPTION, expenses.getDescription());
        contentValues.put(DatabaseHelper.COL_AMOUNTH, expenses.getAmounth());
        long result = db.update(DatabaseHelper.TB_EXPENSES, contentValues, DatabaseHelper.COL_ID+" = ? ", new String[]{expenses.getId()});
        return result != -1;
    }

    public static boolean deleteExpenses(DatabaseHelper databaseHelper, String Id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(DatabaseHelper.TB_EXPENSES, DatabaseHelper.COL_ID+" = ? ", new String[]{Id});
        return result != -1;
    }
}
