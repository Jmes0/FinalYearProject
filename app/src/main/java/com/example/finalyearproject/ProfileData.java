package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProfileData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_database";
    private static final String TABLE1 = "table1";
    private static final String TABLE2 = "table2";

    public ProfileData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " +  TABLE1 + "(id INTEGER PRIMARY KEY,login TEXT)";
        String table2 = "CREATE TABLE " +  TABLE2 + "(id INTEGER PRIMARY KEY,userdata TEXT)";
        db.execSQL(table1);
        db.execSQL(table2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
        onCreate(db);
    }

    public boolean insert(String product, String qty, String price) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put("login", product);
        sqLiteDatabase .insert(TABLE1, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put("userdata", product);
        sqLiteDatabase .insert(TABLE2, null, values2);
        return true;
    }

    public ArrayList getLogin() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE1, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int csr = cursor.getColumnIndex("login");
            arrayList.add(cursor.getString(csr));
            cursor.moveToNext();
        }
        return arrayList;
    }

}
