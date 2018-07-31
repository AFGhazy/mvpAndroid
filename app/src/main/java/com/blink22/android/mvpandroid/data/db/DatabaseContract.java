package com.blink22.android.mvpandroid.data.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ahmedghazy on 7/31/18.
 */

public class DatabaseContract {
    //Database schema information
    public static final String TABLE_TODOS = "todos";

    public static final class TodoColumns implements BaseColumns {
        //Task ID
        public static final String ID = "id";
        //Task description
        public static final String DESCRIPTION = "description";
        //Completed marker
        public static final String TITLE = "title";
        //Priority marker
        public static final String DONE = "done";
    }

    //Unique authority string for the content provider
    public static final String CONTENT_AUTHORITY = "com.blink22.android.mvpandroid";

    /* Sort order constants */
    //Priority first, Completed last, the rest by date
    public static final String DEFAULT_SORT = String.format("%s ASC",
            TodoColumns.TITLE);

    //Base content Uri for accessing the provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_TODOS)
            .build();

    public static Uri getElementUri(int id) {
        return new Uri.Builder().scheme("content")
                .authority(CONTENT_AUTHORITY)
                .appendPath(TABLE_TODOS)
                .appendPath(Integer.toString(id))
                .build();
    }

    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
}