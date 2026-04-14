package org.designpattern.practiceQuestions.ScreenTimeTracker.model;

import java.time.LocalDate;

// One usage record: how long an app was used on a given date
public class AppUsage {
    public String appId;
    public LocalDate date;
    public int durationMinutes;

    public AppUsage(String appId, LocalDate date, int durationMinutes) {
        this.appId = appId;
        this.date = date;
        this.durationMinutes = durationMinutes;
    }

    public String toCSV() {
        return appId + "," + date.toString() + "," + durationMinutes;
    }

    public static AppUsage fromCSV(String line) {
        String[] parts = line.split(",");
        return new AppUsage(parts[0], LocalDate.parse(parts[1]), Integer.parseInt(parts[2]));
    }
}
