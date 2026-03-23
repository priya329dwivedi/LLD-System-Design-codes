/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.concreteClass;

import org.designpattern.strategyPattern.practiceStrategy.RidePricingSystem.Interface.PricingStretegy;

public class SurgePricing implements PricingStretegy {
    private static final double BASE_FARE = 50.0;
    private static final double PER_KM_RATE = 10.0;

    @Override
    public double calculateFare(double distance, double demand, int numberOfPassengers) {
        double fare = (BASE_FARE + (distance * PER_KM_RATE)) * demand;
        System.out.println("Surge Pricing: Base fare = " + BASE_FARE + ", Distance = " + distance + " km, Demand multiplier = " + demand + ", Fare = " + fare);
        return fare;
    }
}
