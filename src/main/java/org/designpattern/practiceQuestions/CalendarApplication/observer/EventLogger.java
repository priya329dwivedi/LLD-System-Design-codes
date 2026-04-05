package org.designpattern.practiceQuestions.CalendarApplication.observer;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;

public class EventLogger implements EventObserver {

    @Override
    public void onEventCreated(Event event) {
        System.out.println("[NOTIFY] Event created: " + event.getTitle() + " → invites sent to " + event.getUserResponses().size() + " user(s)");
    }

    @Override
    public void onEventUpdated(Event event) {
        System.out.println("[NOTIFY] Event updated: " + event.getTitle());
    }

    @Override
    public void onEventDeleted(Event event) {
        System.out.println("[NOTIFY] Event deleted: " + event.getTitle() + " → all participants notified");
    }

    @Override
    public void onUserResponded(Event event, String userId, String response) {
        System.out.println("[NOTIFY] User " + userId + " " + response + " event: " + event.getTitle());
    }
}
