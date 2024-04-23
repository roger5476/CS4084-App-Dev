package com.example.shapeshifter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostsDataSource {

    private SQLiteDatabase database;
    private PostsDbHelper dbHelper;
    private static final String TAG = "PostsDataSource";

    public PostsDataSource(Context context) {
        dbHelper = new PostsDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addPost(String content, String username, String timestamp) {
        ContentValues values = new ContentValues();
        values.put(PostsContract.PostEntry.COLUMN_CONTENT, content);
        values.put(PostsContract.PostEntry.COLUMN_USERNAME, username);
        values.put(PostsContract.PostEntry.COLUMN_TIMESTAMP, timestamp);

        try {
            return database.insert(PostsContract.PostEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e(TAG, "Error inserting post", e);
            return -1; // Return -1 to indicate failure
        }
    }

    private String formatTimestamp(String timestamp) {
        try {
            long tsLong = Long.parseLong(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date(tsLong);
            return sdf.format(date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return timestamp; // Return the original timestamp if parsing fails
        }
    }

    public List<String> getAllPosts() {
        List<String> postsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                PostsContract.PostEntry.TABLE_NAME,
                new String[]{PostsContract.PostEntry.COLUMN_CONTENT, PostsContract.PostEntry.COLUMN_USERNAME, PostsContract.PostEntry.COLUMN_TIMESTAMP},
                null,
                null,
                null,
                null,
                PostsContract.PostEntry.COLUMN_ID + " DESC"  // Sort by COLUMN_ID in descending order
        );

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(PostsContract.PostEntry.COLUMN_CONTENT));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(PostsContract.PostEntry.COLUMN_USERNAME));
            @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(PostsContract.PostEntry.COLUMN_TIMESTAMP));

            // Format the timestamp
            String formattedTimestamp = formatTimestamp(timestamp);

            String formattedPost = content + " (" + username + " - " + formattedTimestamp + ")";
            postsList.add(formattedPost);
        }

        cursor.close();
        return postsList;
    }
    public void clearAllPosts() {
        database.delete(PostsContract.PostEntry.TABLE_NAME, null, null);
    }
}
