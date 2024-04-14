package com.example.shapeshifter.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.shapeshifter.data.UserContract.UserEntry;

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
                UserContract.UserEntry.TABLE_NAME + " (" +
                UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserContract.UserEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                UserContract.UserEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                UserContract.UserEntry.COLUMN_NAME_USERNAME + " TEXT NOT NULL, " +
                UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL" +
                // Add more columns as needed
                ");";

        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        // Recreate the table
        onCreate(db);
    }

    // Method to insert a new user into the database
    public long insertUser(String firstName, String lastName, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_FIRST_NAME, firstName);
        values.put(UserContract.UserEntry.COLUMN_LAST_NAME, lastName);
        values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password);
        // Add more key-value pairs for other columns as needed
        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    // Method to query a user by username
    public Cursor getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_FIRST_NAME,
                UserContract.UserEntry.COLUMN_LAST_NAME,
                UserContract.UserEntry.COLUMN_NAME_USERNAME,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
                // Add more columns as needed
        };
        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    // Method to update an existing user's password
    public int updateUserPassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, newPassword);
        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };
        int count = db.update(
                UserContract.UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        db.close();
        return count;
    }

    // Method to delete a user by username
    public int deleteUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };
        int deletedRows = db.delete(
                UserContract.UserEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        db.close();
        return deletedRows;
    }

    public String getFirstNameByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {UserContract.UserEntry.COLUMN_FIRST_NAME};
        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        String firstName = null;
        if (cursor != null && cursor.moveToFirst()) {
            firstName = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_FIRST_NAME));
            cursor.close();
        }
        return firstName;
    }

    @SuppressLint("Range")
    public String getUserFirstName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String firstName = null;

        // Define a projection that specifies which columns from the database you will actually use after this query
        String[] projection = {
                UserEntry.COLUMN_FIRST_NAME
        };

        // Filter results WHERE "username" = 'inputUsername'
        String selection = UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {username};

        // Query the database
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (null to return all columns)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // Don't group the rows
                null,                   // Don't filter by row groups
                null                    // The sort order
        );

        // Extract the first name from the cursor if the cursor is not null and contains data
        if (cursor != null && cursor.moveToFirst()) {
            firstName = cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_FIRST_NAME));
            cursor.close();
        }

        // Return the first name
        return firstName;
    }
}