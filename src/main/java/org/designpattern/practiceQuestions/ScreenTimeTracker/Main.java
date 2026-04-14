package org.designpattern.practiceQuestions.ScreenTimeTracker;

import org.designpattern.practiceQuestions.ScreenTimeTracker.model.App;
import org.designpattern.practiceQuestions.ScreenTimeTracker.model.AppCategory;
import org.designpattern.practiceQuestions.ScreenTimeTracker.model.AppUsage;
import org.designpattern.practiceQuestions.ScreenTimeTracker.repository.AppRepository;
import org.designpattern.practiceQuestions.ScreenTimeTracker.repository.UsageRepository;
import org.designpattern.practiceQuestions.ScreenTimeTracker.service.ScreenTimeService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        AppRepository appRepo = new AppRepository();
        UsageRepository usageRepo = new UsageRepository("wellbeing_data.csv");
        ScreenTimeService service = new ScreenTimeService(appRepo, usageRepo);

        // Register apps
        service.registerApp(new App("A1", "Instagram", AppCategory.SOCIAL_MEDIA));
        service.registerApp(new App("A2", "VS Code", AppCategory.PRODUCTIVITY));
        service.registerApp(new App("A3", "YouTube", AppCategory.ENTERTAINMENT));

        // Seed 6 days of historical usage
        LocalDate today = LocalDate.now();
        usageRepo.save(new AppUsage("A1", today.minusDays(6), 45));
        usageRepo.save(new AppUsage("A2", today.minusDays(5), 180));
        usageRepo.save(new AppUsage("A3", today.minusDays(4), 90));
        usageRepo.save(new AppUsage("A2", today.minusDays(3), 240));
        usageRepo.save(new AppUsage("A1", today.minusDays(2), 60));
        usageRepo.save(new AppUsage("A3", today.minusDays(1), 120));

        // Seed today's usage directly (in a real app, startSession/stopSession is called by the OS)
        usageRepo.save(new AppUsage("A2", today, 45));
        usageRepo.save(new AppUsage("A1", today, 30));

        // Display stats
        service.displayDailyStats(today);
        service.displayWeeklyStats();
    }
}
