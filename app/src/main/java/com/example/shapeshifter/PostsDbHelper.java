package com.example.shapeshifter;

import static com.example.shapeshifter.PostsContract.PostEntry.COLUMN_TIMESTAMP;
import static com.example.shapeshifter.PostsContract.PostEntry.COLUMN_USERNAME;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PostsDbHelper extends SQLiteOpenHelper {

    public PostsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_NAME = "posts.db";
    private static final int DATABASE_VERSION = 2;

    // Table name and column names
    public static final String TABLE_POSTS = "Posts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CONTENT = "content";

    // Create table query
    private static final String SQL_CREATE_POSTS_TABLE =
            "CREATE TABLE " + TABLE_POSTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CONTENT + " TEXT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_TIMESTAMP + " TEXT)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        onCreate(db);
    }

    public int getMaxUserId() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + PostsContract.PostEntry.COLUMN_ID + ") FROM " + PostsContract.PostEntry.TABLE_NAME, null);
        int maxUserId = 0;
        if (cursor.moveToFirst()) {
            maxUserId = cursor.getInt(0);
        }
        cursor.close();
        return maxUserId;
    }
}
