package org.designpattern.practiceQuestions.SubscriptionPlatform.repository;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanRepository {
    Map<String, Plan> store = new HashMap<>();

    public void save(Plan plan) {
        store.put(plan.id, plan);
    }

    public Plan get(String planId) {
        return store.get(planId);
    }

    public List<Plan> getAll() {
        return new ArrayList<>(store.values());
    }
}
