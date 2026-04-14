package org.designpattern.practiceQuestions.CalendarApplication;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.EventType;
import org.designpattern.practiceQuestions.CalendarApplication.model.ResponseStatus;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;
import org.designpattern.practiceQuestions.CalendarApplication.model.User;
import org.designpattern.practiceQuestions.CalendarApplication.repository.EventRepository;
import org.designpattern.practiceQuestions.CalendarApplication.repository.UserRepository;
import org.designpattern.practiceQuestions.CalendarApplication.service.CalendarService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserRepository userRepo = new UserRepository();
        EventRepository eventRepo = new EventRepository();
        CalendarService service = new CalendarService(userRepo, eventRepo);

        // ========== Register Users ==========
        User alice = new User("U1", "Alice", "alice@email.com");
        User bob   = new User("U2", "Bob",   "bob@email.com");
        User carol = new User("U3", "Carol", "carol@email.com");
        service.registerUser(alice);
        service.registerUser(bob);
        service.registerUser(carol);

        // ========== Create Meeting ==========
        System.out.println("=== Create Meeting ===");
        LocalDateTime t10 = LocalDateTime.of(2026, 3, 25, 10, 0);
        LocalDateTime t11 = LocalDateTime.of(2026, 3, 25, 11, 0);
        Event standup = service.createEvent("Daily Standup", t10, t11, "Room A",
                "U1", Arrays.asList("U2", "U3"), EventType.MEETING);
        System.out.println("Created: " + standup.title + " [" + standup.id + "]");

        // ========== Create Birthday (non-meeting type) ==========
        System.out.println("\n=== Create Birthday ===");
        Event birthday = service.createEvent("Bob's Birthday", t10, t11, null,
                "U2", Arrays.asList("U1"), EventType.BIRTHDAY);
        System.out.println("Created: " + birthday.title + " [" + birthday.id + "]");

        // ========== Create Reminder ==========
        System.out.println("\n=== Create Reminder ===");
        LocalDateTime t14 = LocalDateTime.of(2026, 3, 25, 14, 0);
        LocalDateTime t1430 = LocalDateTime.of(2026, 3, 25, 14, 30);
        Event reminder = service.createEvent("Submit Report", t14, t1430, null,
                "U1", Arrays.asList(), EventType.REMINDER);
        System.out.println("Created: " + reminder.title + " [" + reminder.id + "]");

        // ========== Respond to Event ==========
        System.out.println("\n=== Respond to Meeting ===");
        service.respondToEvent(standup.id, "U2", ResponseStatus.ACCEPTED);
        service.respondToEvent(standup.id, "U3", ResponseStatus.REJECTED);
        System.out.println("U2 response: " + standup.userResponses.get("U2"));
        System.out.println("U3 response: " + standup.userResponses.get("U3"));

        // ========== Get Calendar ==========
        System.out.println("\n=== Alice's Calendar ===");
        for (Event e : service.getCalendar("U1")) {
            System.out.println("  [" + e.type + "] " + e.title + " | " + e.start + " - " + e.end);
        }

        // ========== Get Event Details ==========
        System.out.println("\n=== Event Details (standup) ===");
        Event details = service.getEventDetails(standup.id);
        System.out.println("  Title: " + details.title);
        System.out.println("  Owner: " + details.ownerId);
        System.out.println("  Responses: " + details.userResponses);

        // ========== Update Event ==========
        System.out.println("\n=== Update Event ===");
        LocalDateTime t1130 = LocalDateTime.of(2026, 3, 25, 11, 30);
        LocalDateTime t12   = LocalDateTime.of(2026, 3, 25, 12, 0);
        service.updateEvent(standup.id, "Sprint Standup", t1130, t12, null);
        System.out.println("Updated title: " + standup.title + " | new time: " + standup.start + " - " + standup.end);

        // ========== Find Common Free Slots ==========
        System.out.println("\n=== Common Free Slots (U1, U2, U3) >= 30 min ===");
        LocalDateTime t13 = LocalDateTime.of(2026, 3, 25, 13, 0);
        service.createEvent("1:1 with Manager", t13, t14, "Room B",
                "U2", Arrays.asList("U3"), EventType.MEETING);

        LocalDateTime dayStart = LocalDateTime.of(2026, 3, 25, 9, 0);
        LocalDateTime dayEnd   = LocalDateTime.of(2026, 3, 25, 18, 0);
        List<TimeSlot> freeSlots = service.findCommonFreeSlots(
                Arrays.asList("U1", "U2", "U3"), 30, dayStart, dayEnd);
        for (TimeSlot slot : freeSlots) {
            System.out.println("  Free: " + slot.start + " - " + slot.end);
        }

        // ========== Delete Event ==========
        System.out.println("\n=== Delete Event ===");
        service.deleteEvent(standup.id);
        System.out.println("Alice's calendar after delete:");
        for (Event e : service.getCalendar("U1")) {
            System.out.println("  " + e.title);
        }
    }
}
