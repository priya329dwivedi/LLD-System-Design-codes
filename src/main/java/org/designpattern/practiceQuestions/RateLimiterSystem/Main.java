package org.designpattern.practiceQuestions.RateLimiterSystem;

import org.designpattern.practiceQuestions.RateLimiterSystem.model.Algorithm;
import org.designpattern.practiceQuestions.RateLimiterSystem.model.RateLimitConfig;
import org.designpattern.practiceQuestions.RateLimiterSystem.observer.ThrottleLogger;
import org.designpattern.practiceQuestions.RateLimiterSystem.repository.UserConfigRepository;
import org.designpattern.practiceQuestions.RateLimiterSystem.strategy.LeakyBucketStrategy;
import org.designpattern.practiceQuestions.RateLimiterSystem.strategy.TokenBucketStrategy;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // ========== Leaky Bucket ==========
        System.out.println("========== Leaky Bucket (capacity=5, leak=2/s) ==========\n");

        UserConfigRepository userConfigRepo = new UserConfigRepository();
        RateLimiterService leakyService = new RateLimiterService(userConfigRepo, new LeakyBucketStrategy());
        leakyService.addObserver(new ThrottleLogger());

        // Free user: 5 capacity, leaks 2/sec
        leakyService.registerUser(new RateLimitConfig("user1", Algorithm.LEAKY_BUCKET, 5, 2.0));
        // Premium user: higher limit
        leakyService.registerUser(new RateLimitConfig("user2", Algorithm.LEAKY_BUCKET, 10, 5.0));

        System.out.println("--- 6 rapid requests for user1 (bucket fills at 5) ---");
        for (int i = 1; i <= 6; i++) {
            leakyService.isAllowed("user1");
        }

        System.out.println("\n--- Waiting 2 seconds to leak ---");
        Thread.sleep(2000);

        System.out.println("\n--- 3 requests after leak ---");
        for (int i = 1; i <= 3; i++) {
            leakyService.isAllowed("user1");
        }

        System.out.println("\n--- user2 (premium) has independent bucket ---");
        for (int i = 1; i <= 5; i++) {
            leakyService.isAllowed("user2");
        }

        // ========== Token Bucket ==========
        System.out.println("\n========== Token Bucket (capacity=3, refill=1/s) ==========\n");

        UserConfigRepository tokenRepo = new UserConfigRepository();
        RateLimiterService tokenService = new RateLimiterService(tokenRepo, new TokenBucketStrategy());
        tokenService.addObserver(new ThrottleLogger());

        tokenService.registerUser(new RateLimitConfig("userA", Algorithm.TOKEN_BUCKET, 3, 1.0));

        System.out.println("--- Burst: 4 requests (bucket starts full at 3) ---");
        for (int i = 1; i <= 4; i++) {
            tokenService.isAllowed("userA");
        }

        System.out.println("\n--- Waiting 2 seconds to refill 2 tokens ---");
        Thread.sleep(2000);

        System.out.println("\n--- 2 requests after refill ---");
        for (int i = 1; i <= 2; i++) {
            tokenService.isAllowed("userA");
        }
    }
}
