/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem;

import org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.UtilityClasses.Ride;
import org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.concreteClass.NormalPricing;
import org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.concreteClass.SharedRidePricing;
import org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.concreteClass.SurgePricing;

public class Main {
    public static void main(String[] args) {
        // Normal Pricing: 15 km ride
        NormalPricing normalPricing = new NormalPricing();
        Ride ride = new Ride(normalPricing, 15, 1.0, 1);
        System.out.println("Total Fare: " + ride.calculateFare());
        System.out.println();

        // Switch to Surge Pricing at runtime: same distance, demand multiplier = 2.0
        SurgePricing surgePricing = new SurgePricing();
        ride.setPricingStretegy(surgePricing);
        System.out.println("Total Fare: " + ride.calculateFare());
        System.out.println();

        // Switch to Shared Ride Pricing at runtime: 3 passengers sharing
        SharedRidePricing sharedRidePricing = new SharedRidePricing();
        Ride sharedRide = new Ride(sharedRidePricing, 15, 1.0, 3);
        System.out.println("Total Fare per person: " + sharedRide.calculateFare());
    }
}
