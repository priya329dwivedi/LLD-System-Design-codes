package org.designpattern.practiceQuestions.CalendarApplication.factory;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.EventType;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;
import org.designpattern.practiceQuestions.CalendarApplication.strategy.ConflictStrategy;
import org.designpattern.practiceQuestions.CalendarApplication.strategy.NoConflictStrategy;
import org.designpattern.practiceQuestions.CalendarApplication.strategy.StrictConflictStrategy;

import java.util.UUID;

public class EventFactory {

    public static Event createEvent(String title, TimeSlot timeSlot, String location, String ownerId, EventType type) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return new Event(id, title, timeSlot, location, ownerId, type);
    }

    public static ConflictStrategy getConflictStrategy(EventType type) {
        switch (type) {
            case MEETING:
                return new StrictConflictStrategy();
            case HOLIDAY:
            case BIRTHDAY:
            case REMINDER:
            default:
                return new NoConflictStrategy();
        }
    }
}
