/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.UtilityClasses;

import lombok.Setter;
import org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.Interface.PricingStretegy;

public class Ride {
    @Setter
    PricingStretegy pricingStretegy;
    double distance;
    double demand;
    int numberOfPassengers;

    public Ride(PricingStretegy pricingStretegy, double distance, double demand, int numberOfPassengers) {
        this.pricingStretegy = pricingStretegy;
        this.distance = distance;
        this.demand = demand;
        this.numberOfPassengers = numberOfPassengers;
    }

    public double calculateFare() {
        return pricingStretegy.calculateFare(distance, demand, numberOfPassengers);
    }

}
