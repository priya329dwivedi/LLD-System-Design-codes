package org.designpattern.practiceQuestions.ScreenTimeTracker.repository;

import org.designpattern.practiceQuestions.ScreenTimeTracker.model.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppRepository {
    Map<String, App> store = new HashMap<>();

    public void save(App app) {
        store.put(app.id, app);
    }

    public App get(String appId) {
        return store.get(appId);
    }

    public List<App> getAll() {
        return new ArrayList<>(store.values());
    }
}
