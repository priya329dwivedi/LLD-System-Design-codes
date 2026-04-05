package org.designpattern.practiceQuestions.CalendarApplication.strategy;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;

import java.util.List;

public class NoConflictStrategy implements ConflictStrategy {

    @Override
    public boolean hasConflict(TimeSlot newSlot, List<Event> existingEvents) {
        return false;
    }
}
