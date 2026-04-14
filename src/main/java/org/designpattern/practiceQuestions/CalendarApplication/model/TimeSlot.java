package org.designpattern.practiceQuestions.CalendarApplication.model;

import java.time.LocalDateTime;

public class TimeSlot {
    public LocalDateTime start;
    public LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
}
