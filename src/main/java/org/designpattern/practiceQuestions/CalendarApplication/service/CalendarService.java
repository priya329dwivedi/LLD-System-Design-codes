package org.designpattern.practiceQuestions.CalendarApplication.service;

import org.designpattern.practiceQuestions.CalendarApplication.factory.EventFactory;
import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.EventType;
import org.designpattern.practiceQuestions.CalendarApplication.model.ResponseStatus;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;
import org.designpattern.practiceQuestions.CalendarApplication.model.User;
import org.designpattern.practiceQuestions.CalendarApplication.observer.EventObserver;
import org.designpattern.practiceQuestions.CalendarApplication.strategy.ConflictStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CalendarService {
    private static CalendarService instance;
    private final Map<String, Event> events;
    private final Map<String, User> users;
    private final List<EventObserver> observers;

    private CalendarService() {
        this.events = new LinkedHashMap<>();
        this.users = new LinkedHashMap<>();
        this.observers = new ArrayList<>();
    }

    public static CalendarService getInstance() {
        if (instance == null) {
            instance = new CalendarService();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void addObserver(EventObserver observer) {
        observers.add(observer);
    }

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    // ========== Create Event ==========
    public Event createEvent(String title, TimeSlot timeSlot, String location, String ownerId, List<String> userIds, EventType type) {
        if (!users.containsKey(ownerId)) {
            System.out.println("Owner " + ownerId + " not found");
            return null;
        }

        // Check conflicts for owner
        ConflictStrategy strategy = EventFactory.getConflictStrategy(type);
        List<Event> ownerEvents = getCalendar(ownerId);
        if (strategy.hasConflict(timeSlot, ownerEvents)) {
            System.out.println("Conflict detected for owner " + users.get(ownerId).getName());
            return null;
        }

        // Check conflicts for each invited user
        for (String userId : userIds) {
            List<Event> userEvents = getCalendar(userId);
            if (strategy.hasConflict(timeSlot, userEvents)) {
                System.out.println("Conflict detected for user " + users.get(userId).getName());
                return null;
            }
        }

        Event event = EventFactory.createEvent(title, timeSlot, location, ownerId, type);
        for (String userId : userIds) {
            event.addUser(userId);
        }
        events.put(event.getId(), event);
        notifyCreated(event);
        return event;
    }

    // ========== Update Event ==========
    public Event updateEvent(String eventId, String requesterId, String title, TimeSlot timeSlot, String location) {
        Event event = events.get(eventId);
        if (event == null) {
            System.out.println("Event " + eventId + " not found");
            return null;
        }
        if (!event.getOwnerId().equals(requesterId)) {
            System.out.println("Only the owner can update this event");
            return null;
        }

        // If time changed, re-check conflicts
        if (timeSlot != null && !timeSlot.getStart().equals(event.getTimeSlot().getStart())) {
            ConflictStrategy strategy = EventFactory.getConflictStrategy(event.getEventType());
            for (String userId : event.getUserResponses().keySet()) {
                List<Event> userEvents = getCalendar(userId);
                userEvents.remove(event);  // exclude current event from conflict check
                if (strategy.hasConflict(timeSlot, userEvents)) {
                    System.out.println("Conflict detected for user " + userId + " on updated time");
                    return null;
                }
            }
            event.setTimeSlot(timeSlot);
        }

        if (title != null) event.setTitle(title);
        if (location != null) event.setLocation(location);
        notifyUpdated(event);
        return event;
    }

    // ========== Delete Event ==========
    public boolean deleteEvent(String eventId, String requesterId) {
        Event event = events.get(eventId);
        if (event == null) {
            System.out.println("Event " + eventId + " not found");
            return false;
        }
        if (!event.getOwnerId().equals(requesterId)) {
            System.out.println("Only the owner can delete this event");
            return false;
        }
        events.remove(eventId);
        notifyDeleted(event);
        return true;
    }

    // ========== Respond to Event ==========
    public boolean respondToEvent(String eventId, String userId, ResponseStatus status) {
        Event event = events.get(eventId);
        if (event == null) {
            System.out.println("Event " + eventId + " not found");
            return false;
        }
        if (!event.hasUser(userId)) {
            System.out.println("User " + userId + " is not part of this event");
            return false;
        }
        if (event.getOwnerId().equals(userId)) {
            System.out.println("Owner cannot change their response");
            return false;
        }
        boolean responded = event.respond(userId, status);
        if (responded) {
            notifyResponded(event, userId, status.name());
        }
        return responded;
    }

    // ========== Get Calendar for a user ==========
    public List<Event> getCalendar(String userId) {
        List<Event> userEvents = new ArrayList<>();
        for (Event event : events.values()) {
            if (event.hasUser(userId)) {
                userEvents.add(event);
            }
        }
        userEvents.sort(Comparator.comparing(e -> e.getTimeSlot().getStart()));
        return userEvents;
    }

    // ========== Get Event Details ==========
    public Event getEventDetails(String eventId) {
        Event event = events.get(eventId);
        if (event == null) {
            System.out.println("Event " + eventId + " not found");
        }
        return event;
    }

    // ========== Find Common Free Slots ==========
    public List<TimeSlot> findCommonFreeSlots(List<String> userIds, long durationMinutes, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        // Collect all busy slots (only meetings count as busy)
        List<TimeSlot> allBusySlots = new ArrayList<>();
        for (String userId : userIds) {
            for (Event event : getCalendar(userId)) {
                if (event.getEventType() == EventType.MEETING) {
                    allBusySlots.add(event.getTimeSlot());
                }
            }
        }

        // Sort by start time
        allBusySlots.sort(Comparator.comparing(TimeSlot::getStart));

        // Merge overlapping busy slots
        List<TimeSlot> merged = new ArrayList<>();
        for (TimeSlot slot : allBusySlots) {
            if (merged.isEmpty() || merged.get(merged.size() - 1).getEnd().isBefore(slot.getStart())) {
                merged.add(slot);
            } else {
                TimeSlot last = merged.get(merged.size() - 1);
                LocalDateTime laterEnd = last.getEnd().isAfter(slot.getEnd()) ? last.getEnd() : slot.getEnd();
                merged.set(merged.size() - 1, new TimeSlot(last.getStart(), laterEnd));
            }
        }

        // Find free gaps >= durationMinutes
        List<TimeSlot> freeSlots = new ArrayList<>();
        LocalDateTime cursor = rangeStart;

        for (TimeSlot busy : merged) {
            if (busy.getStart().isAfter(cursor)) {
                TimeSlot gap = new TimeSlot(cursor, busy.getStart());
                if (gap.getDurationMinutes() >= durationMinutes) {
                    freeSlots.add(gap);
                }
            }
            if (busy.getEnd().isAfter(cursor)) {
                cursor = busy.getEnd();
            }
        }

        // Check gap after last busy slot
        if (cursor.isBefore(rangeEnd)) {
            TimeSlot gap = new TimeSlot(cursor, rangeEnd);
            if (gap.getDurationMinutes() >= durationMinutes) {
                freeSlots.add(gap);
            }
        }

        return freeSlots;
    }

    // ========== Display Calendar ==========
    public void displayCalendar(String userId) {
        User user = users.get(userId);
        String name = (user != null) ? user.getName() : userId;
        List<Event> calendar = getCalendar(userId);

        System.out.println("\n--- Calendar for " + name + " (" + calendar.size() + " events) ---");
        if (calendar.isEmpty()) {
            System.out.println("  (no events)");
        }
        for (Event event : calendar) {
            String status = event.getOwnerId().equals(userId) ? "OWNER" : event.getUserResponses().get(userId).name();
            System.out.println("  " + event + " [" + status + "]");
        }
    }

    // ========== Display Event Details ==========
    public void displayEventDetails(String eventId) {
        Event event = getEventDetails(eventId);
        if (event == null) return;

        System.out.println("\n--- Event Details ---");
        System.out.println("  Title    : " + event.getTitle());
        System.out.println("  Type     : " + event.getEventType());
        System.out.println("  Time     : " + event.getTimeSlot());
        System.out.println("  Location : " + (event.getLocation() != null ? event.getLocation() : "N/A"));
        System.out.println("  Owner    : " + event.getOwnerId());
        System.out.println("  Responses:");
        for (Map.Entry<String, ResponseStatus> entry : event.getUserResponses().entrySet()) {
            User user = users.get(entry.getKey());
            String name = (user != null) ? user.getName() : entry.getKey();
            System.out.println("    " + name + " → " + entry.getValue());
        }
    }

    private void notifyCreated(Event event) {
        for (EventObserver observer : observers) observer.onEventCreated(event);
    }

    private void notifyUpdated(Event event) {
        for (EventObserver observer : observers) observer.onEventUpdated(event);
    }

    private void notifyDeleted(Event event) {
        for (EventObserver observer : observers) observer.onEventDeleted(event);
    }

    private void notifyResponded(Event event, String userId, String response) {
        for (EventObserver observer : observers) observer.onUserResponded(event, userId, response);
    }
}
