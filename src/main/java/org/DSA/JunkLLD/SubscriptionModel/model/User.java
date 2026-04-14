
package org.DSA.JunkLLD.SubscriptionModel.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class User {
    String id;
    String name;

    public User( String name) {
        this.id = UUID.randomUUID().toString().substring(0,8);
        this.name = name;
    }
}
