package com.example.nanark.advance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nanark on 11/28/16.
 */
public class Income {
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

    public static Cursor listIncome(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TMPTB_INCOME, null);
        return cursor;
    }

    public static int totalIncome(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM("+DatabaseHelper.COL_AMOUNTH+") FROM " + DatabaseHelper.TMPTB_INCOME, null);
        if(cursor.moveToFirst())
            return cursor.getInt(0);
        else
            return 0;
    }

    public static boolean saveIncome(DatabaseHelper databaseHelper, Income income) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_DATE, income.getDate());
        contentValues.put(DatabaseHelper.COL_DESCRIPTION, income.getDescription());
        contentValues.put(DatabaseHelper.COL_AMOUNTH, income.getAmounth());
        long result = db.insert(DatabaseHelper.TMPTB_INCOME, null, contentValues);
        return result != -1;
    }

    public static boolean updateIncome(DatabaseHelper databaseHelper, Income income) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_ID, income.getId());
        contentValues.put(DatabaseHelper.COL_DATE, income.getDate());
        contentValues.put(DatabaseHelper.COL_DESCRIPTION, income.getDescription());
        contentValues.put(DatabaseHelper.COL_AMOUNTH, income.getAmounth());
        long result = db.update(DatabaseHelper.TMPTB_INCOME, contentValues, DatabaseHelper.COL_ID+" = ? ", new String[]{income.getId()});
        return result != -1;
    }

    public static boolean deleteIncome(DatabaseHelper databaseHelper, String Id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(DatabaseHelper.TMPTB_INCOME, DatabaseHelper.COL_ID+" = ? ", new String[]{Id});
        return result != -1;
    }
}
