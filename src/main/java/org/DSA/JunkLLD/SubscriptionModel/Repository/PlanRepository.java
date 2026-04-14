
package org.DSA.JunkLLD.SubscriptionModel.Repository;

import org.DSA.JunkLLD.SubscriptionModel.model.Plan;

import java.util.HashMap;
import java.util.Map;

public class PlanRepository {
    Map<String, Plan> plans = new HashMap<>();

    public void save(Plan plan){
        plans.put(plan.getId(), plan);
    }
    public Plan getPlan(String planId){
        return plans.get(planId);
    }
}
