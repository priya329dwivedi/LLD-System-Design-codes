package org.designpattern.practiceQuestions.CalendarApplication;

import org.designpattern.practiceQuestions.CalendarApplication.model.EventType;
import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.ResponseStatus;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;
import org.designpattern.practiceQuestions.CalendarApplication.model.User;
import org.designpattern.practiceQuestions.CalendarApplication.observer.EventLogger;
import org.designpattern.practiceQuestions.CalendarApplication.service.CalendarService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CalendarService service = CalendarService.getInstance();
        service.addObserver(new EventLogger());

        // ========== Register Users ==========
        User priya = new User("U1", "Priya", "priya@email.com");
        User haotami = new User("U2", "Haotami", "haotami@email.com");
        User yogita = new User("U3", "Yogita", "yogita@email.com");
        service.registerUser(priya);
        service.registerUser(haotami);
        service.registerUser(yogita);

        // ========== Create Meeting ==========
        System.out.println("========== Create Meeting ==========\n");

        LocalDateTime mar25_10am = LocalDateTime.of(2026, 3, 25, 10, 0);
        LocalDateTime mar25_11am = LocalDateTime.of(2026, 3, 25, 11, 0);
        Event standup = service.createEvent("Daily Standup", new TimeSlot(mar25_10am, mar25_11am),
                "Room A", "U1", Arrays.asList("U2", "U3"), EventType.MEETING);

        // ========== Create Birthday (no conflict check) ==========
        System.out.println("\n========== Create Birthday (overlapping time — allowed) ==========\n");

        Event birthday = service.createEvent("Priya's Birthday", new TimeSlot(mar25_10am, mar25_11am),
                null, "U2", Arrays.asList("U1", "U3"), EventType.BIRTHDAY);

        // ========== Conflict: Create another meeting at same time ==========
        System.out.println("\n========== Conflict: Another meeting at same time ==========\n");

        Event conflicting = service.createEvent("Design Review", new TimeSlot(mar25_10am, mar25_11am),
                "Room B", "U1", Arrays.asList("U2"), EventType.MEETING);
        System.out.println("Result: " + (conflicting == null ? "REJECTED (conflict)" : "Created"));

        // ========== Create Reminder ==========
        System.out.println("\n========== Create Reminder ==========\n");

        LocalDateTime mar25_2pm = LocalDateTime.of(2026, 3, 25, 14, 0);
        LocalDateTime mar25_230pm = LocalDateTime.of(2026, 3, 25, 14, 30);
        Event reminder = service.createEvent("Submit Report", new TimeSlot(mar25_2pm, mar25_230pm),
                null, "U1", List.of(), EventType.REMINDER);

        // ========== Respond to Events ==========
        System.out.println("\n========== Respond to Events ==========\n");

        service.respondToEvent(standup.getId(), "U2", ResponseStatus.ACCEPTED);
        service.respondToEvent(standup.getId(), "U3", ResponseStatus.REJECTED);

        // Edge case: user not part of event
        System.out.println();
        service.respondToEvent(standup.getId(), "U99", ResponseStatus.ACCEPTED);

        // ========== Event Details ==========
        System.out.println();
        service.displayEventDetails(standup.getId());

        // ========== View Calendars ==========
        System.out.println("\n========== Calendars ==========");
        service.displayCalendar("U1");
        service.displayCalendar("U2");
        service.displayCalendar("U3");

        // ========== Update Event ==========
        System.out.println("\n========== Update Event (change title + time) ==========\n");

        LocalDateTime mar25_11_30am = LocalDateTime.of(2026, 3, 25, 11, 30);
        LocalDateTime mar25_12pm = LocalDateTime.of(2026, 3, 25, 12, 0);
        service.updateEvent(standup.getId(), "U1", "Sprint Standup", new TimeSlot(mar25_11_30am, mar25_12pm), null);
        service.displayEventDetails(standup.getId());

        // Edge case: non-owner tries to update
        System.out.println();
        service.updateEvent(standup.getId(), "U2", "Hacked Title", null, null);

        // ========== Find Common Free Slots ==========
        System.out.println("\n========== Common Free Slots (U1, U2, U3) — 30 min ==========\n");

        // Add another meeting for U2
        LocalDateTime mar25_1pm = LocalDateTime.of(2026, 3, 25, 13, 0);
        service.createEvent("1:1 with Manager", new TimeSlot(mar25_1pm, mar25_2pm),
                "Room C", "U2", Arrays.asList("U3"), EventType.MEETING);

        LocalDateTime dayStart = LocalDateTime.of(2026, 3, 25, 9, 0);
        LocalDateTime dayEnd = LocalDateTime.of(2026, 3, 25, 18, 0);
        List<TimeSlot> freeSlots = service.findCommonFreeSlots(Arrays.asList("U1", "U2", "U3"), 30, dayStart, dayEnd);

        System.out.println("Free slots (>= 30 min) on 2026-03-25:");
        for (TimeSlot slot : freeSlots) {
            System.out.println("  " + slot);
        }

        // ========== Delete Event ==========
        System.out.println("\n========== Delete Event ==========\n");

        // Edge case: non-owner tries to delete
        service.deleteEvent(standup.getId(), "U3");

        // Owner deletes
        service.deleteEvent(standup.getId(), "U1");
        service.displayCalendar("U1");
    }
}
