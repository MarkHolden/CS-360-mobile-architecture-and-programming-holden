package com.holden.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class EventReaderDbHelper extends SQLiteOpenHelper {
    public EventReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner class that defines the table contents */
    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_GROUP = "event_group";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_START = "start_date_time";
        public static final String COLUMN_NAME_END = "end_date_time";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EventReaderDbHelper.EventEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " TEXT PRIMARY KEY," +
                    EventReaderDbHelper.EventEntry.COLUMN_NAME_NAME + " TEXT, " +
                    EventReaderDbHelper.EventEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    EventReaderDbHelper.EventEntry.COLUMN_NAME_GROUP + " TEXT, " +
                    EventReaderDbHelper.EventEntry.COLUMN_NAME_LOCATION + " TEXT, " +
                    EventReaderDbHelper.EventEntry.COLUMN_NAME_START + " TEXT, " +
                    EventReaderDbHelper.EventEntry.COLUMN_NAME_END + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventReaderDbHelper.EventEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EventReader.db";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
