
package org.DSA.JunkLLD.SubscriptionModel;

import org.DSA.JunkLLD.SubscriptionModel.Observer.Email;
import org.DSA.JunkLLD.SubscriptionModel.Observer.Sms;
import org.DSA.JunkLLD.SubscriptionModel.Repository.ContentRepository;
import org.DSA.JunkLLD.SubscriptionModel.Repository.PlanRepository;
import org.DSA.JunkLLD.SubscriptionModel.Repository.SubscriptionRepository;
import org.DSA.JunkLLD.SubscriptionModel.Repository.UserRepository;
import org.DSA.JunkLLD.SubscriptionModel.Service.SubscriptionService;
import org.DSA.JunkLLD.SubscriptionModel.Strategy.CreditPayment;
import org.DSA.JunkLLD.SubscriptionModel.Strategy.UPIPayment;
import org.DSA.JunkLLD.SubscriptionModel.model.Content;
import org.DSA.JunkLLD.SubscriptionModel.model.Plan;
import org.DSA.JunkLLD.SubscriptionModel.model.PlantTier;
import org.DSA.JunkLLD.SubscriptionModel.model.User;

public class Main {
    public static void main(String[] args) {
        ContentRepository contentRepository= new ContentRepository();
        PlanRepository planRepository = new PlanRepository();
        SubscriptionRepository subscriptionRepository= new SubscriptionRepository();
        UserRepository userRepository= new UserRepository();

        Plan planA = new Plan("Plan A", PlantTier.FREE, 0, 30);
        Plan planB = new Plan("Plan B", PlantTier.BASIC, 80, 30);
        Plan planC = new Plan("Plan C", PlantTier.ADVANCED, 160, 30);

        Content content1 = new Content("Pokemon", PlantTier.FREE);
        Content content2 = new Content("Mamla Legal Hai", PlantTier.BASIC);
        Content content3 = new Content("Squid Games", PlantTier.ADVANCED);

        User user1 = new User("Hansa");
        User user2 = new User("Pranksuuuuuuu");
        User user3 = new User("Ayush");

        SubscriptionService service= new SubscriptionService(contentRepository,planRepository,userRepository,subscriptionRepository);
        service.addObservers(new Email("priya@gmail.com"));
        service.addObservers(new Sms("7905398329"));
        service.addContent(content1);
        service.addContent(content2);
        service.addContent(content3);

        service.registerUser(user1);
        service.registerUser(user2);
        service.registerUser(user3);

        service.addPlans(planA);
        service.addPlans(planB);
        service.addPlans(planC);

        service.subscribe(user1.getId(), planA.getId(), new CreditPayment("234523455432"));
        service.subscribe(user2.getId(), planB.getId(), new UPIPayment("priya@ptsbi"));
        service.subscribe(user3.getId(), planC.getId(), new CreditPayment("68687867656756"));

        service.canAccessContent(content2.getId(), user3.getId());
        service.canAccessContent(content3.getId(), user1.getId());
        service.canAccessContent(content1.getId(), user2.getId());

    }
}
