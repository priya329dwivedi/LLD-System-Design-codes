package org.designpattern.practiceQuestions.CalendarApplication.strategy;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.EventType;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;

import java.util.List;

public class StrictConflictStrategy implements ConflictStrategy {

    @Override
    public boolean hasConflict(TimeSlot newSlot, List<Event> existingEvents) {
        for (Event event : existingEvents) {
            if (event.getEventType() == EventType.MEETING && newSlot.overlapsWith(event.getTimeSlot())) {
                return true;
            }
        }
        return false;
    }
}
