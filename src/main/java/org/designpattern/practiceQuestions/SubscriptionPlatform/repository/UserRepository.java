package org.designpattern.practiceQuestions.SubscriptionPlatform.repository;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    Map<String, User> store = new HashMap<>();

    public void save(User user) {
        store.put(user.id, user);
    }

    public User get(String userId) {
        return store.get(userId);
    }
}
