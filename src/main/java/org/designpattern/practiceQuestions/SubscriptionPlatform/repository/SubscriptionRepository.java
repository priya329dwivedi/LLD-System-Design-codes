package org.designpattern.practiceQuestions.SubscriptionPlatform.repository;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Subscription;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.SubscriptionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionRepository {
    Map<String, Subscription> store = new HashMap<>();  // subscriptionId -> Subscription

    public void save(Subscription subscription) {
        store.put(subscription.id, subscription);
    }

    public Subscription get(String subscriptionId) {
        return store.get(subscriptionId);
    }

    // Returns the current active subscription for a user (null if none)
    public Subscription getActiveSub(String userId) {
        for (Subscription sub : store.values()) {
            if (sub.userId.equals(userId) && sub.status == SubscriptionStatus.ACTIVE) {
                return sub;
            }
        }
        return null;
    }

    // Full subscription history for a user
    public List<Subscription> getHistory(String userId) {
        List<Subscription> history = new ArrayList<>();
        for (Subscription sub : store.values()) {
            if (sub.userId.equals(userId)) {
                history.add(sub);
            }
        }
        return history;
    }
}
