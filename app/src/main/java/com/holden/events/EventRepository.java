package com.holden.events;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventRepository {
    private SQLiteDatabase db;
    private Context _context;

    public EventRepository(Context context) {
        EventReaderDbHelper dbHelper = new EventReaderDbHelper(context);
        db = dbHelper.getWritableDatabase();
        _context = context;
    }

    public void create(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventReaderDbHelper.EventEntry._ID, String.valueOf(event.id));
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_NAME, event.name);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_DESCRIPTION, event.description);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_GROUP, event.group);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_LOCATION, event.location);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_START, event.startDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_END, event.endDateTime.format(DateTimeFormatter.ISO_DATE_TIME));

        long result = db.insertOrThrow(EventReaderDbHelper.EventEntry.TABLE_NAME, null, values);
        long dong = result;
    }

    public Event read(UUID id) {
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { id.toString() };

        Cursor cursor = db.query(EventReaderDbHelper.EventEntry.TABLE_NAME, getProjection(),
            selection, selectionArgs, null, null, null);

        return readCursor(cursor).get(0);
    }

    public List<Event> list() {
        String sortOrder = EventReaderDbHelper.EventEntry.COLUMN_NAME_START + " DESC";

        Cursor cursor = db.query(EventReaderDbHelper.EventEntry.TABLE_NAME, getProjection(),
                null, null, null, null, sortOrder);

        return readCursor(cursor);
    }

    public void update(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_NAME, event.name);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_DESCRIPTION, event.description);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_GROUP, event.group);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_LOCATION, event.location);
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_START, String.valueOf(event.startDateTime));
        values.put(EventReaderDbHelper.EventEntry.COLUMN_NAME_END, String.valueOf(event.endDateTime));

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { event.id.toString() };

        db.update(EventReaderDbHelper.EventEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public void delete(UUID id) {
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { id.toString() };
        db.delete(EventReaderDbHelper.EventEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void seed() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            Event event = new Event(_context);
            event.name = "This is event #" + i;
            event.description = "This is event #" + i + " description";
            event.startDateTime = LocalDateTime.now().plusHours(1);
            event.endDateTime = LocalDateTime.now().plusHours(2);
            event.group = event.groupsOptions[random.nextInt(event.groupsOptions.length)];
            event.location = event.locationsOptions[random.nextInt(event.locationsOptions.length)];

            create(event);
        }
    }

    /**
     * Specifies the columns used from the query.
     * @return Query projection.
     */
    private String[] getProjection() {
        return new String[] {
            BaseColumns._ID,
            EventReaderDbHelper.EventEntry.COLUMN_NAME_NAME,
            EventReaderDbHelper.EventEntry.COLUMN_NAME_DESCRIPTION,
            EventReaderDbHelper.EventEntry.COLUMN_NAME_GROUP,
            EventReaderDbHelper.EventEntry.COLUMN_NAME_LOCATION,
            EventReaderDbHelper.EventEntry.COLUMN_NAME_START,
            EventReaderDbHelper.EventEntry.COLUMN_NAME_END,
        };
    }

    private List<Event> readCursor(Cursor cursor) {
        List<Event> events = new ArrayList<>();
        while(cursor.moveToNext()) {
            Event event = new Event(_context);
            event.id = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID)));
            event.name = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderDbHelper.EventEntry.COLUMN_NAME_NAME));
            event.description = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderDbHelper.EventEntry.COLUMN_NAME_DESCRIPTION));
            event.location = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderDbHelper.EventEntry.COLUMN_NAME_LOCATION));
            event.group = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderDbHelper.EventEntry.COLUMN_NAME_GROUP));
            String startString = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderDbHelper.EventEntry.COLUMN_NAME_START));
            event.startDateTime = LocalDateTime.parse(startString, DateTimeFormatter.ISO_DATE_TIME);
            String endString = cursor.getString(cursor.getColumnIndexOrThrow(EventReaderDbHelper.EventEntry.COLUMN_NAME_END));
            event.endDateTime = LocalDateTime.parse(endString, DateTimeFormatter.ISO_DATE_TIME);

            events.add(event);
        }
        cursor.close();
        return events;
    }

}
