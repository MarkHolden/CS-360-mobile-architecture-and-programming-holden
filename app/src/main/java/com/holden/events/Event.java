package com.holden.events;

import android.content.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Event {
    public UUID id;
    public String name;
    public String description;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String group;
    public String location;

    private static Context _context;
    private static DateTimeFormatter _dateFormatter;
    private static DateTimeFormatter _timeFormatter;

    public Event(Context context)
    {
        _context = context;
        _dateFormatter = DateTimeFormatter.ofPattern(context.getString(R.string.human_readable_date_format));
        _timeFormatter = DateTimeFormatter.ofPattern(context.getString(R.string.human_readable_time_format));
    }

    public String getStartTimeString() {
        return _context.getString(R.string.start_date_time, startTime.format(_dateFormatter), startTime.format(_timeFormatter));
    }

    public String getEndTimeString() {
        return _context.getString(R.string.end_date_time, endTime.format(_dateFormatter), endTime.format(_timeFormatter));
    }
}
