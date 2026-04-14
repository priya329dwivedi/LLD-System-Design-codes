
package org.DSA.JunkLLD.SubscriptionModel.Repository;

import org.DSA.JunkLLD.SubscriptionModel.model.Subscription;
import org.DSA.JunkLLD.SubscriptionModel.model.SubscriptionStatus;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionRepository {

    Map<String, Subscription> subscriptionMap = new HashMap<>();

    public void save(Subscription subscription){
        subscriptionMap.put(subscription.getId(),subscription);
    }

    public Subscription getSubscription(String subscriptionId){
        return subscriptionMap.get(subscriptionId);
    }

    public Subscription getActiveSubscription(String userId){
        for(Subscription subs: subscriptionMap.values()){
            if(subs.getUserId().equals(userId) && subs.getStatus().equals(SubscriptionStatus.ACTIVE)){
                return subs;
            }
        }
        return null;
    }
}
