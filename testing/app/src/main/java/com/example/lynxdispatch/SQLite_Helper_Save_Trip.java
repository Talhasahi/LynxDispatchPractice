package com.example.lynxdispatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLite_Helper_Save_Trip extends SQLiteOpenHelper {
    SQLite_Helper_Save_Trip(Context context) {
        super(context, "lynxDispatch_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS saved_Trips (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, name TEXT, contact_no  TEXT, pichUpTime DATETIME, pichUpAddress TEXT, DropOfaddress TEXT, NoOfPassanger INTEGER)");
        //db.execSQL("CREATE TABLE IF NOT EXISTS home_news (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, image TEXT, content TEXT, likeText TEXT, shareText TEXT, date_entry TEXT, newstag TEXT, t_temp TEXT, l_temp TEXT, image_slider_array TEXT, category TEXT, views TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    boolean insertData(String userId, String Name, String content, String pichUpTime, String pichUpAddress, String DropOfaddress, Integer NoOfPassanger) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("name", Name);
        cv.put("contact_no", content);
        cv.put("pichUpTime", pichUpTime);
        cv.put("pichUpAddress", pichUpAddress);
        cv.put("DropOfaddress", DropOfaddress);
        cv.put("NoOfPassanger", NoOfPassanger);

        long result_insert = db.insert("saved_Trips", null, cv);
        return result_insert != -1;  // if(result_insert != -1) return false / true
    }

    Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM saved_Trips ORDER BY id ASC", null);
    }

    Integer deleteResult(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("saved_Trips", "user_id = ?", new String[]{title});
    }

    Cursor getData(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM saved_Trips ", new String[]{title});
    }

    Cursor getDataWithDate(String date) {
        String s = date + " 00:00:00";
        String s2 = date + " 23:59:59";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from saved_Trips where pichUpTime >= Datetime(?) and pichUpTime <= Datetime(?)", new String[]{s, s2});
    }
}