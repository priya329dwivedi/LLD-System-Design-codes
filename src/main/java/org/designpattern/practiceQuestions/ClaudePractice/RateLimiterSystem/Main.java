package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem;

import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.factory.RateLimiterFactory;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.observer.AlertNotifier;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.observer.AnalyticsLogger;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.service.RateLimiterService;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy.RateLimitStrategy;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy.SlidingWindowStrategy;

public class Main {
    public static void main(String[] args) {

        // ========== Free tier: Fixed Window (5 requests/min) ==========
        System.out.println("========== Free Tier (Fixed Window: 5 req/min) ==========\n");

        RateLimitStrategy freeStrategy = RateLimiterFactory.createStrategy("free");
        RateLimiterService service = RateLimiterService.getInstance(freeStrategy, 2);
        service.addObserver(new AlertNotifier());
        service.addObserver(new AnalyticsLogger());

        // Simulate 8 requests from user "priya"
        for (int i = 1; i <= 8; i++) {
            boolean allowed = service.handleRequest("priya");
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }

        // Different user has independent limit
        System.out.println("\n--- Different user ---");
        System.out.println("haotami request: " + (service.handleRequest("haotami") ? "ALLOWED" : "REJECTED"));

        // ========== Switch to Sliding Window ==========
        System.out.println("\n========== Switching to Sliding Window (10 req/min) ==========\n");

        service.setStrategy(new SlidingWindowStrategy(10, 60000));

        for (int i = 1; i <= 12; i++) {
            boolean allowed = service.handleRequest("yogita");
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }

        // ========== Premium tier: new service ==========
        System.out.println("\n========== Premium Tier (Sliding Window: 20 req/min) ==========\n");

        RateLimiterService.resetInstance();
        RateLimitStrategy premiumStrategy = RateLimiterFactory.createStrategy("premium");
        RateLimiterService premiumService = RateLimiterService.getInstance(premiumStrategy, 3);
        premiumService.addObserver(new AlertNotifier());

        for (int i = 1; i <= 22; i++) {
            boolean allowed = premiumService.handleRequest("enterprise_user");
            if (!allowed || i > 17) {
                System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
            }
        }
    }
}
