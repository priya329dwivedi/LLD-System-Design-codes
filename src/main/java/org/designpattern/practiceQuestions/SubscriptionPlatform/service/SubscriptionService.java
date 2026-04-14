package org.designpattern.practiceQuestions.SubscriptionPlatform.service;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Content;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Plan;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.PlanTier;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Subscription;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.SubscriptionStatus;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.User;
import org.designpattern.practiceQuestions.SubscriptionPlatform.observer.SubscriptionObserver;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.ContentRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.PlanRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.SubscriptionRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.UserRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.strategy.PaymentStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubscriptionService {
    UserRepository userRepo;
    PlanRepository planRepo;
    SubscriptionRepository subscriptionRepo;
    ContentRepository contentRepo;
    List<SubscriptionObserver> observers;

    public SubscriptionService(UserRepository userRepo, PlanRepository planRepo,
                               SubscriptionRepository subscriptionRepo, ContentRepository contentRepo) {
        this.userRepo = userRepo;
        this.planRepo = planRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.contentRepo = contentRepo;
        this.observers = new ArrayList<>();
    }

    public void addObserver(SubscriptionObserver observer) {
        observers.add(observer);
    }

    // ===== Setup =====

    public void registerUser(User user) {
        userRepo.save(user);
    }

    public void addPlan(Plan plan) {
        planRepo.save(plan);
    }

    public void addContent(Content content) {
        contentRepo.save(content);
    }

    // ===== API: Subscribe =====

    public Subscription subscribe(String userId, String planId, PaymentStrategy payment) {
        User user = userRepo.get(userId);
        Plan plan = planRepo.get(planId);

        payment.processPayment(plan.pricePerMonth);

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(plan.billingCycleDays);
        Subscription sub = new Subscription(UUID.randomUUID().toString(), userId, planId,
                SubscriptionStatus.ACTIVE, start, end);
        subscriptionRepo.save(sub);

        for (SubscriptionObserver o : observers) o.onSubscribed(user, plan);
        System.out.println("[Sub] " + user.name + " subscribed to " + plan.name + " (ends " + end + ")");
        return sub;
    }

    // ===== API: Cancel =====

    public void cancel(String subscriptionId) {
        Subscription sub = subscriptionRepo.get(subscriptionId);
        User user = userRepo.get(sub.userId);
        Plan plan = planRepo.get(sub.planId);

        sub.status = SubscriptionStatus.CANCELLED;
        sub.autoRenew = false;

        for (SubscriptionObserver o : observers) o.onCancelled(user, plan);
        System.out.println("[Sub] " + user.name + "'s " + plan.name + " cancelled (access until " + sub.endDate + ")");
    }

    // ===== API: Upgrade =====

    public void upgrade(String subscriptionId, String newPlanId, PaymentStrategy payment) {
        Subscription sub = subscriptionRepo.get(subscriptionId);
        User user = userRepo.get(sub.userId);
        Plan oldPlan = planRepo.get(sub.planId);
        Plan newPlan = planRepo.get(newPlanId);

        double priceDiff = newPlan.pricePerMonth - oldPlan.pricePerMonth;
        payment.processPayment(priceDiff);  // charge only the difference

        sub.planId = newPlanId;

        for (SubscriptionObserver o : observers) o.onPlanChanged(user, oldPlan, newPlan);
        System.out.println("[Sub] " + user.name + " upgraded: " + oldPlan.name + " → " + newPlan.name);
    }

    // ===== API: Downgrade =====

    public void downgrade(String subscriptionId, String newPlanId) {
        Subscription sub = subscriptionRepo.get(subscriptionId);
        User user = userRepo.get(sub.userId);
        Plan oldPlan = planRepo.get(sub.planId);
        Plan newPlan = planRepo.get(newPlanId);

        sub.planId = newPlanId;  // takes effect immediately (no refund in happy path)

        for (SubscriptionObserver o : observers) o.onPlanChanged(user, oldPlan, newPlan);
        System.out.println("[Sub] " + user.name + " downgraded: " + oldPlan.name + " → " + newPlan.name);
    }

    // ===== API: Renew =====

    public void renew(String subscriptionId, PaymentStrategy payment) {
        Subscription sub = subscriptionRepo.get(subscriptionId);
        User user = userRepo.get(sub.userId);
        Plan plan = planRepo.get(sub.planId);

        payment.processPayment(plan.pricePerMonth);
        sub.endDate = sub.endDate.plusDays(plan.billingCycleDays);
        sub.status = SubscriptionStatus.ACTIVE;

        for (SubscriptionObserver o : observers) o.onRenewed(user, plan);
        System.out.println("[Sub] " + user.name + "'s " + plan.name + " renewed until " + sub.endDate);
    }

    // ===== API: Content Access Check =====

    public boolean canAccess(String userId, String contentId) {
        Content content = contentRepo.get(contentId);
        Subscription sub = subscriptionRepo.getActiveSub(userId);

        PlanTier userTier;
        if (sub == null) {
            userTier = PlanTier.FREE;  // no subscription = free tier only
        } else {
            userTier = planRepo.get(sub.planId).tier;
        }

        boolean allowed = userTier.ordinal() >= content.requiredTier.ordinal();
        System.out.println("[Access] " + userId + " (" + userTier + ") → \"" + content.title
                + "\" [" + content.requiredTier + "]: " + (allowed ? "GRANTED" : "DENIED"));
        return allowed;
    }

    // ===== API: View Plans =====

    public void listPlans() {
        System.out.println("\n--- Available Plans ---");
        for (Plan plan : planRepo.getAll()) {
            System.out.println("  " + plan);
        }
    }

    // ===== API: Subscription History =====

    public void showHistory(String userId) {
        User user = userRepo.get(userId);
        System.out.println("\n--- Subscription History: " + user.name + " ---");
        for (Subscription sub : subscriptionRepo.getHistory(userId)) {
            Plan plan = planRepo.get(sub.planId);
            System.out.println("  " + plan.name + " | " + sub.status
                    + " | " + sub.startDate + " → " + sub.endDate);
        }
    }
}
