package org.designpattern.practiceQuestions.CalendarApplication.repository;

import org.designpattern.practiceQuestions.CalendarApplication.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRepository {
    Map<String, Event> store = new HashMap<>();

    public void save(Event event) {
        store.put(event.id, event);
    }

    public Event get(String id) {
        return store.get(id);
    }

    public void delete(String id) {
        store.remove(id);
    }

    public List<Event> getAll() {
        return new ArrayList<>(store.values());
    }
}
