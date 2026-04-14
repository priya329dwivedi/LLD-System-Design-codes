package org.designpattern.practiceQuestions.CalendarApplication.service;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.EventType;
import org.designpattern.practiceQuestions.CalendarApplication.model.ResponseStatus;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;
import org.designpattern.practiceQuestions.CalendarApplication.model.User;
import org.designpattern.practiceQuestions.CalendarApplication.repository.EventRepository;
import org.designpattern.practiceQuestions.CalendarApplication.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarService {
    UserRepository userRepo;
    EventRepository eventRepo;
    int idCounter = 1;

    public CalendarService(UserRepository userRepo, EventRepository eventRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public void registerUser(User user) {
        userRepo.save(user);
    }

    public Event createEvent(String title, LocalDateTime start, LocalDateTime end,
                             String location, String ownerId, List<String> invitees, EventType type) {
        String id = "E" + idCounter++;
        Event event = new Event(id, title, start, end, location, ownerId, type);
        for (String userId : invitees) {
            event.userResponses.put(userId, ResponseStatus.PENDING);
        }
        eventRepo.save(event);
        return event;
    }

    public void updateEvent(String eventId, String title, LocalDateTime start, LocalDateTime end, String location) {
        Event event = eventRepo.get(eventId);
        if (title != null) event.title = title;
        if (start != null) event.start = start;
        if (end != null) event.end = end;
        if (location != null) event.location = location;
    }

    public void deleteEvent(String eventId) {
        eventRepo.delete(eventId);
    }

    public void respondToEvent(String eventId, String userId, ResponseStatus status) {
        Event event = eventRepo.get(eventId);
        event.userResponses.put(userId, status);
    }

    public List<Event> getCalendar(String userId) {
        List<Event> result = new ArrayList<>();
        for (Event event : eventRepo.getAll()) {
            if (event.ownerId.equals(userId) || event.userResponses.containsKey(userId)) {
                result.add(event);
            }
        }
        return result;
    }

    public Event getEventDetails(String eventId) {
        return eventRepo.get(eventId);
    }

    public List<TimeSlot> findCommonFreeSlots(List<String> userIds, long durationMinutes,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        // Collect all meeting busy slots across users
        List<TimeSlot> busySlots = new ArrayList<>();
        for (String userId : userIds) {
            for (Event event : getCalendar(userId)) {
                if (event.type == EventType.MEETING) {
                    busySlots.add(new TimeSlot(event.start, event.end));
                }
            }
        }

        // Sort by start time
        busySlots.sort((a, b) -> a.start.compareTo(b.start));

        // Merge overlapping busy slots
        List<TimeSlot> merged = new ArrayList<>();
        for (TimeSlot slot : busySlots) {
            if (merged.isEmpty() || merged.get(merged.size() - 1).end.isBefore(slot.start)) {
                merged.add(new TimeSlot(slot.start, slot.end));
            } else {
                TimeSlot last = merged.get(merged.size() - 1);
                if (slot.end.isAfter(last.end)) last.end = slot.end;
            }
        }

        // Find gaps >= durationMinutes
        List<TimeSlot> freeSlots = new ArrayList<>();
        LocalDateTime cursor = rangeStart;
        for (TimeSlot busy : merged) {
            if (busy.start.isAfter(cursor)) {
                long mins = Duration.between(cursor, busy.start).toMinutes();
                if (mins >= durationMinutes) freeSlots.add(new TimeSlot(cursor, busy.start));
            }
            if (busy.end.isAfter(cursor)) cursor = busy.end;
        }
        if (cursor.isBefore(rangeEnd)) {
            long mins = Duration.between(cursor, rangeEnd).toMinutes();
            if (mins >= durationMinutes) freeSlots.add(new TimeSlot(cursor, rangeEnd));
        }

        return freeSlots;
    }
}
