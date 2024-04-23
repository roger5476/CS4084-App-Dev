package com.example.shapeshifter;

import android.provider.BaseColumns;

public final class PostsContract {

    private PostsContract() {} // Private constructor to prevent instantiation

    public static final class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "posts";
        public static final String COLUMN_ID = "_id"; // Primary key
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
