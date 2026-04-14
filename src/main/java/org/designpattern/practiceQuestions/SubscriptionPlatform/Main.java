package org.designpattern.practiceQuestions.SubscriptionPlatform;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Content;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Plan;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.PlanTier;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Subscription;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.User;
import org.designpattern.practiceQuestions.SubscriptionPlatform.observer.NotificationService;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.ContentRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.PlanRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.SubscriptionRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.repository.UserRepository;
import org.designpattern.practiceQuestions.SubscriptionPlatform.service.SubscriptionService;
import org.designpattern.practiceQuestions.SubscriptionPlatform.strategy.CreditCardPayment;
import org.designpattern.practiceQuestions.SubscriptionPlatform.strategy.UPIPayment;

public class Main {
    public static void main(String[] args) {

        // ===== Wire up =====
        PlanRepository planRepo         = new PlanRepository();
        UserRepository userRepo         = new UserRepository();
        SubscriptionRepository subRepo  = new SubscriptionRepository();
        ContentRepository contentRepo   = new ContentRepository();

        SubscriptionService service = new SubscriptionService(userRepo, planRepo, subRepo, contentRepo);
        service.addObserver(new NotificationService());

        // ===== Seed Plans =====
        Plan free    = new Plan("P0", "Free",    PlanTier.FREE,    0.0,   30);
        Plan basic   = new Plan("P1", "Basic",   PlanTier.BASIC,   8.99,  30);
        Plan premium = new Plan("P2", "Premium", PlanTier.PREMIUM, 15.99, 30);
        service.addPlan(free);
        service.addPlan(basic);
        service.addPlan(premium);

        // ===== Seed Users =====
        service.registerUser(new User("U1", "Alice", "alice@email.com"));
        service.registerUser(new User("U2", "Bob",   "bob@email.com"));

        // ===== Seed Content =====
        service.addContent(new Content("C1", "Free Documentary",  PlanTier.FREE));
        service.addContent(new Content("C2", "The Grand Tour",    PlanTier.BASIC));
        service.addContent(new Content("C3", "4K Movie Premiere", PlanTier.PREMIUM));

        // ===== List Plans =====
        service.listPlans();

        // ===== Subscribe =====
        System.out.println("\n===== Subscribe =====");
        Subscription aliceSub = service.subscribe("U1", "P1", new CreditCardPayment("4111111111111234"));
        Subscription bobSub   = service.subscribe("U2", "P0", new UPIPayment("bob@upi"));

        // ===== Content Access Check =====
        System.out.println("\n===== Content Access =====");
        service.canAccess("U1", "C1");  // Alice (BASIC)   → Free doc    → GRANTED
        service.canAccess("U1", "C2");  // Alice (BASIC)   → Basic show  → GRANTED
        service.canAccess("U1", "C3");  // Alice (BASIC)   → 4K premiere → DENIED
        service.canAccess("U2", "C1");  // Bob   (FREE)    → Free doc    → GRANTED
        service.canAccess("U2", "C2");  // Bob   (FREE)    → Basic show  → DENIED

        // ===== Upgrade =====
        System.out.println("\n===== Upgrade =====");
        service.upgrade(aliceSub.id, "P2", new CreditCardPayment("4111111111111234"));
        service.canAccess("U1", "C3");  // Alice (PREMIUM) → 4K premiere → GRANTED now

        // ===== Renew =====
        System.out.println("\n===== Renew =====");
        service.renew(aliceSub.id, new CreditCardPayment("4111111111111234"));

        // ===== Downgrade =====
        System.out.println("\n===== Downgrade =====");
        service.downgrade(aliceSub.id, "P1");

        // ===== Cancel =====
        System.out.println("\n===== Cancel =====");
        service.cancel(bobSub.id);

        // ===== Subscription History =====
        service.showHistory("U1");
        service.showHistory("U2");
    }
}
