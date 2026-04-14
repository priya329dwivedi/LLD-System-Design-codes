
package org.DSA.JunkLLD.SubscriptionModel.Repository;

import org.DSA.JunkLLD.SubscriptionModel.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    Map<String, User> users= new HashMap<>();

    public void save(User user){
        users.put(user.getId(),user);
    }

    public User getUser(String userId){
        return users.get(userId);
    }


}
