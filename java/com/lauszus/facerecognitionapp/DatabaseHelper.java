package com.lauszus.facerecognitionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lauszus on 2018-08-31.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "image.db";
    public static final String TABLE_NAME = "img_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "NATIVEOBJ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,NATIVEOBJ TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertdata(String name, String surname) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_2, name);
        contentvalues.put(COL_3, surname);
        long result = db.insert(TABLE_NAME, null, contentvalues); //long??null??
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);//null??
        return res;
    }

    public boolean update(String id, String name, String surname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_1, id);
        contentvalues.put(COL_2, name);
        contentvalues.put(COL_3, surname);
        db.update(TABLE_NAME, contentvalues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deletedata(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});

    }
}