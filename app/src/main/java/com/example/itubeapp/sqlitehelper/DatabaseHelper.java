package com.example.itubeapp.sqlitehelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        String CREATE_TABLE_COMMAND = "CREATE TABLE "
                + Util.TABLE_NAME + "("
                + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Util.NAME +" TEXT,"
                + Util.USERNAME +" TEXT,"
                + Util.PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_COMMAND);
        // Create truck table
        String CREATE_TABLE2_COMMAND = "CREATE TABLE "
                + Util.TABLE2_NAME + "("
                + Util.VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Util.NAME +" TEXT,"
                + Util.URL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE2_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE2_NAME);
        onCreate(db);
    }

    // INSERT
    public long insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, user.getName());
        contentValues.put(Util.USERNAME, user.getUsername());
        contentValues.put(Util.PASSWORD, user.getDescription());

        long rowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return rowId;
    }
    public long insertVideo(Video video){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, video.getName());
        contentValues.put(Util.URL, video.getVideoUrl());

        long rowId = db.insert(Util.TABLE2_NAME, null, contentValues);
        db.close();
        return rowId;
    }
    // GET
    public boolean getUsers(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID},
                Util.USERNAME + " =? and " +
                        Util.PASSWORD + " =?",
                new String[]{username, password}, null, null, null);

        int numOfRows = cursor.getCount();
        if (numOfRows > 0)
            return true;
        else
            return false;
    }
    public ArrayList<Video> getMyVideos(String name){
        ArrayList<Video> videos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE2_NAME + " WHERE " + Util.NAME + "='" + name + "'", null);
        if (cursor.moveToFirst()) {
            do {
                videos.add(new Video(
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return videos;
    }
}
