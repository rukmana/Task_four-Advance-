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

    public static final String DB_NAME = "Advance.db";
    public static final int DB_VERSION = 1;

    public static final String TB_EXPENSES = "Expenses";
    public static final String TMPTB_EXPENSES = "tmpExpenses";
    public static final String TB_INCOME = "Income";
    public static final String TMPTB_INCOME = "tmpIncome";

    public static final String COL_ID = "Id";
    public static final String COL_DATE = "Date";
    public static final String COL_AMOUNTH = "Amounth";
    public static final String COL_DESCRIPTION = "Description";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable(TB_EXPENSES));
        db.execSQL(createTable(TMPTB_EXPENSES));
        db.execSQL(createTable(TB_INCOME));
        db.execSQL(createTable(TMPTB_INCOME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TMPTB_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TB_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TMPTB_INCOME);
        onCreate(db);
    }

    private String createTable(String TABLE_NAME){
        String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " DATE DEFAULT CURRENT_DATE, " +
                COL_AMOUNTH + " INTEGER, " +
                COL_DESCRIPTION + " TEXT )";
        return TABLE_CREATE;
    }
}