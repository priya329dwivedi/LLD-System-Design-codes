package org.designpattern.practiceQuestions.CalendarApplication.model;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Event {
    private final String id;
    @Setter private String title;
    @Setter private TimeSlot timeSlot;
    @Setter private String location;
    private final String ownerId;
    private final EventType eventType;
    private final Map<String, ResponseStatus> userResponses;  // userId → response

    public Event(String id, String title, TimeSlot timeSlot, String location, String ownerId, EventType eventType) {
        this.id = id;
        this.title = title;
        this.timeSlot = timeSlot;
        this.location = location;
        this.ownerId = ownerId;
        this.eventType = eventType;
        this.userResponses = new LinkedHashMap<>();
    }

    public void addUser(String userId) {
        userResponses.put(userId, ResponseStatus.PENDING);
    }

    public boolean hasUser(String userId) {
        return ownerId.equals(userId) || userResponses.containsKey(userId);
    }

    public boolean respond(String userId, ResponseStatus status) {
        if (!userResponses.containsKey(userId)) return false;
        userResponses.put(userId, status);
        return true;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = timeSlot.getStart().format(fmt) + " - " + timeSlot.getEnd().format(fmt);
        String loc = (location != null && !location.isEmpty()) ? " @ " + location : "";
        return "[" + eventType + "] " + title + " | " + time + loc + " (id: " + id + ")";
    }
}
