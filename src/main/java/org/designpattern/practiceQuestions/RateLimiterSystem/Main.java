package org.designpattern.practiceQuestions.RateLimiterSystem;

import org.designpattern.practiceQuestions.RateLimiterSystem.factory.RateLimiterFactory;
import org.designpattern.practiceQuestions.RateLimiterSystem.observer.ThrottleLogger;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // ========== Leaky Bucket Rate Limiter ==========
        // Capacity: 5 requests, Leak rate: 2 requests/second
        System.out.println("========== Leaky Bucket Rate Limiter ==========\n");

        RateLimiterService service = RateLimiterFactory.createRateLimiter("leaky_bucket", 5, 2.0);
        ThrottleLogger logger = new ThrottleLogger();
        service.addObserver(logger);

        // Send 5 rapid requests for user1 — all should be allowed (fills the bucket)
        System.out.println("--- Sending 5 rapid requests for user1 ---");
        for (int i = 1; i <= 5; i++) {
            boolean allowed = service.isAllowed("user1");
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }

        // 6th request should be rejected (bucket full)
        System.out.println("\n--- 6th request for user1 (bucket full) ---");
        boolean allowed = service.isAllowed("user1");
        System.out.println("Request 6: " + (allowed ? "ALLOWED" : "REJECTED"));

        // Wait 2 seconds — bucket leaks 4 units (2/sec * 2sec)
        System.out.println("\n--- Waiting 2 seconds for bucket to leak ---");
        Thread.sleep(2000);

        // Now requests should be allowed again
        System.out.println("\n--- Sending requests after waiting ---");
        for (int i = 1; i <= 3; i++) {
            allowed = service.isAllowed("user1");
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }

        // Different user should have their own bucket
        System.out.println("\n--- user2 has independent bucket ---");
        for (int i = 1; i <= 5; i++) {
            allowed = service.isAllowed("user2");
            System.out.println("user2 Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }
    }
}
