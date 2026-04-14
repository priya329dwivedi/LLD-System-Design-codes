package org.designpattern.practiceQuestions.CalendarApplication.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Event {
    public String id;
    public String title;
    public LocalDateTime start;
    public LocalDateTime end;
    public String location;
    public String ownerId;
    public EventType type;
    public Map<String, ResponseStatus> userResponses; // inviteeId -> response

    public Event(String id, String title, LocalDateTime start, LocalDateTime end,
                 String location, String ownerId, EventType type) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.location = location;
        this.ownerId = ownerId;
        this.type = type;
        this.userResponses = new HashMap<>();
    }
}
