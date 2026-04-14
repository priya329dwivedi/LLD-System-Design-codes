package org.DSA.JunkLLD.models;

import org.designpattern.practiceQuestions.CalendarApplication.model.ResponseStatus;

import java.util.Map;

public class Event {
    String title;
    Timeslot timeslot;
    String ownerId;
    EventType eventType;
    Map<String, ResponseStatus> userResponses;

    public Event(String title, Timeslot timeslot, String ownerId, EventType eventType, Map<String, ResponseStatus> userResponses) {
        this.title = title;
        this.timeslot = timeslot;
        this.ownerId = ownerId;
        this.eventType = eventType;
        this.userResponses = userResponses;
    }

}
