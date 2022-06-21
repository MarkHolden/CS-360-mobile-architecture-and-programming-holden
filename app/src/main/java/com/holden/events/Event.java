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
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
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
        startDateTime = LocalDateTime.now();
        endDateTime = startDateTime;
    }

    public String getStartDateTimeString() {
        return _context.getString(R.string.start_date_time, startDateTime.format(_dateFormatter), startDateTime.format(_timeFormatter));
    }

    public String getEndDateTimeString() {
        return _context.getString(R.string.end_date_time, endDateTime.format(_dateFormatter), endDateTime.format(_timeFormatter));
    }

    public String getStartTimeString() {
        return startDateTime.format(_timeFormatter);
    }

    public String getEndTimeString() {
        return endDateTime.format(_timeFormatter);
    }

    public String getStartDateString() {
        return startDateTime.format(_dateFormatter);
    }

    public String getEndDateString() {
        return endDateTime.format(_dateFormatter);
    }
}
