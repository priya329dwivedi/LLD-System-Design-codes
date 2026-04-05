package org.designpattern.practiceQuestions.CalendarApplication.strategy;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;
import org.designpattern.practiceQuestions.CalendarApplication.model.TimeSlot;

import java.util.List;

public interface ConflictStrategy {
    boolean hasConflict(TimeSlot newSlot, List<Event> existingEvents);
}
