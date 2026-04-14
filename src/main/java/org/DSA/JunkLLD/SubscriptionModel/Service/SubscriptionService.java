
package org.DSA.JunkLLD.SubscriptionModel.Service;

import org.DSA.JunkLLD.SubscriptionModel.Observer.Notificationservice;
import org.DSA.JunkLLD.SubscriptionModel.Repository.ContentRepository;
import org.DSA.JunkLLD.SubscriptionModel.Repository.PlanRepository;
import org.DSA.JunkLLD.SubscriptionModel.Repository.SubscriptionRepository;
import org.DSA.JunkLLD.SubscriptionModel.Repository.UserRepository;
import org.DSA.JunkLLD.SubscriptionModel.Strategy.PaymentStrategy;
import org.DSA.JunkLLD.SubscriptionModel.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionService {
    ContentRepository contentRepository;
    PlanRepository planRepository;
    UserRepository userRepository;
    SubscriptionRepository subscriptionRepository;
    List<Notificationservice> observers;

    public SubscriptionService(ContentRepository contentRepository, PlanRepository planRepository,
                               UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.contentRepository = contentRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.observers= new ArrayList<>();
    }

    public void registerUser(User user){
        userRepository.save(user);
    }

    public void addPlans(Plan plan){
        planRepository.save(plan);
    }

    public void addContent(Content content){
        contentRepository.save(content);
    }

    public void addObservers(Notificationservice notificationservice){
        observers.add(notificationservice);
    }

    public void subscribe(String userId, String planId, PaymentStrategy strategy){
        Plan plan = planRepository.getPlan(planId);
        User user = userRepository.getUser(userId);
        strategy.pay(plan.getPricePerMonth());
        LocalDate startDate= LocalDate.now();
        LocalDate endDate= LocalDate.now().plusDays(plan.getBillingCycle());
        Subscription subscription= new Subscription(userId,planId,startDate,endDate);
        subscriptionRepository.save(subscription);
        notifySubscription(user.getName(),plan.getName());
    }

    public void cancelSubscription(String subscriptionId){
        Subscription subscription=subscriptionRepository.getSubscription(subscriptionId);
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        notifyCancelSubscription(subscriptionId);
    }

    private void notifyCancelSubscription(String subscriptionId) {
        for(Notificationservice ob: observers){
            ob.cancelSubscription(subscriptionId);
        }
    }

    private void notifySubscription(String userName,String planName){
        for(Notificationservice ob: observers){
            ob.onSubscribe(userName,planName);
        }
    }

    public void canAccessContent(String contentId, String userId){
        Content content= contentRepository.getContent(contentId);
        Subscription sub = subscriptionRepository.getActiveSubscription(userId);
        Plan plan = planRepository.getPlan(sub.getPlanId());
        if(plan.getPlanTier().ordinal()>= content.getPlantTier().ordinal()){
            System.out.println("We can start watching");

        }
        else{
            System.out.println("Access NOT Allowed ");
        }
    }
}
