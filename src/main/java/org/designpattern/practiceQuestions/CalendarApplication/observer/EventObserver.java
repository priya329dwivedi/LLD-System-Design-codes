package org.designpattern.practiceQuestions.CalendarApplication.observer;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;

public interface EventObserver {
    void onEventCreated(Event event);
    void onEventUpdated(Event event);
    void onEventDeleted(Event event);
    void onUserResponded(Event event, String userId, String response);
}
