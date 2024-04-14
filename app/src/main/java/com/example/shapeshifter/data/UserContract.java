package com.example.shapeshifter.data;

import android.provider.BaseColumns;

public final class UserContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UserContract() {}

    // Inner class that defines the table contents
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";

        // Add more columns as needed
    }
}
