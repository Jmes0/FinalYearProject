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
    private static final String TABLE1 = "userData";
    private static final String TABLE2 = "loginData";

    public ProfileData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userData = "CREATE TABLE " +  TABLE1 + "id" + "origin" + "Destination" + "(id INTEGER PRIMARY KEY,login TEXT)";
        String loginData = "CREATE TABLE " +  TABLE2 + "id" + "username" + "password" + "(id INTEGER PRIMARY KEY,userdata TEXT)";
        db.execSQL(userData);
        db.execSQL(loginData);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
        onCreate(db);
    }

    public boolean insertLocation(String origin, String destination) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues locationValues = new ContentValues();
        locationValues.put("id", "origin");
        sqLiteDatabase .insert(TABLE1, null, locationValues);
        return true;
    }

    public boolean checkLogin(String origin, String destination) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put("login", "username");
        sqLiteDatabase .insert(TABLE1, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put("userdata", "userdata");
        sqLiteDatabase .insert(TABLE1, null, values2);
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

    public void getPassword(String str) {

    }

}
