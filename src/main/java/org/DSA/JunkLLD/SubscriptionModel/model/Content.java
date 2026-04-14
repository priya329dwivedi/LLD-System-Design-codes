
package org.DSA.JunkLLD.SubscriptionModel.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Content {
    String id;
    String name;
    PlantTier plantTier;

    public Content(String name, PlantTier plantTier) {
        this.id = UUID.randomUUID().toString().substring(0,8);
        this.name = name;
        this.plantTier = plantTier;
    }
}
