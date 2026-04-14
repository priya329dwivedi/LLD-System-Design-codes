package org.designpattern.practiceQuestions.CalendarApplication.repository;

import org.designpattern.practiceQuestions.CalendarApplication.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    Map<String, User> store = new HashMap<>();

    public void save(User user) {
        store.put(user.id, user);
    }

    public User get(String id) {
        return store.get(id);
    }
}
