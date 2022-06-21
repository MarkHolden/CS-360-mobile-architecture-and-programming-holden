package com.holden.events;

import android.content.Context;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Event implements Serializable {
    public UUID id;
    public String name;
    public String description;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String group;
    public String location;
    public String[] groupsOptions = { "The Mars Society", "Piping Plover Convention", "WWDC - World Wrestling Developer Conference" };
    public String[] locationsOptions = { "Madrone", "Cottonwood", "Larch", "Juniper", "Laurel", "Cedar", "Maple", "Birch" };

    private static Context _context;
    private static DateTimeFormatter _dateFormatter;
    private static DateTimeFormatter _timeFormatter;

    public Event(Context context)
    {
        id = UUID.randomUUID();
        _context = context;
        _dateFormatter = DateTimeFormatter.ofPattern(context.getString(R.string.human_readable_date_format));
        _timeFormatter = DateTimeFormatter.ofPattern(context.getString(R.string.human_readable_time_format));
    }

    public String getStartDateTimeString() {
        return _context.getString(R.string.start_date_time, startTime.format(_dateFormatter), startTime.format(_timeFormatter));
    }

    public String getEndDateTimeString() {
        return _context.getString(R.string.end_date_time, endTime.format(_dateFormatter), endTime.format(_timeFormatter));
    }

    public String getStartTimeString() {
        return startTime.format(_timeFormatter);
    }

    public String getEndTimeString() {
        return endTime.format(_timeFormatter);
    }

    public String getStartDateString() {
        return startTime.format(_dateFormatter);
    }

    public String getEndDateString() {
        return endTime.format(_dateFormatter);
    }
}
