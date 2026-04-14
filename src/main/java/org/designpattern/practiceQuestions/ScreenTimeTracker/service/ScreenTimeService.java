package org.designpattern.practiceQuestions.ScreenTimeTracker.service;

import org.designpattern.practiceQuestions.ScreenTimeTracker.model.App;
import org.designpattern.practiceQuestions.ScreenTimeTracker.model.AppUsage;
import org.designpattern.practiceQuestions.ScreenTimeTracker.repository.AppRepository;
import org.designpattern.practiceQuestions.ScreenTimeTracker.repository.UsageRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ScreenTimeService {
    AppRepository appRepo;
    UsageRepository usageRepo;
    Map<String, Long> activeSessions;  // appId -> session start time in ms

    public ScreenTimeService(AppRepository appRepo, UsageRepository usageRepo) {
        this.appRepo = appRepo;
        this.usageRepo = usageRepo;
        this.activeSessions = new HashMap<>();
        // On startup: prune records older than 7 days
        usageRepo.pruneOlderThan(LocalDate.now().minusDays(6));
    }

    public void registerApp(App app) {
        appRepo.save(app);
    }

    public void startSession(String appId) {
        activeSessions.put(appId, System.currentTimeMillis());
        System.out.println("[Start] " + appRepo.get(appId).name);
    }

    public void stopSession(String appId) {
        long startMs = activeSessions.remove(appId);
        int minutes = (int) ((System.currentTimeMillis() - startMs) / 60000);
        if (minutes < 1) minutes = 1;  // floor to 1 min for demo
        AppUsage usage = new AppUsage(appId, LocalDate.now(), minutes);
        usageRepo.save(usage);
        System.out.println("[Stop]  " + appRepo.get(appId).name + " — " + minutes + " min recorded");
    }

    public void displayDailyStats(LocalDate date) {
        System.out.println("\n--- Daily Stats: " + date + " ---");
        for (App app : appRepo.getAll()) {
            int minutes = 0;
            for (AppUsage u : usageRepo.getByApp(app.id)) {
                if (u.date.equals(date)) minutes += u.durationMinutes;
            }
            if (minutes > 0) {
                System.out.println("  " + app.name + " [" + app.category + "]: " + minutes + " min");
            }
        }
    }

    public void displayWeeklyStats() {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);
        System.out.println("\n--- Weekly Stats (" + weekStart + " to " + today + ") ---");
        for (App app : appRepo.getAll()) {
            int total = 0;
            for (AppUsage u : usageRepo.getByApp(app.id)) {
                if (!u.date.isBefore(weekStart)) total += u.durationMinutes;
            }
            if (total > 0) {
                System.out.println("  " + app.name + " [" + app.category + "]: "
                        + total + " min total, avg " + total / 7 + " min/day");
            }
        }
    }
}
