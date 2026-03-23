/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery;

public class StandardDelivery implements DeliveryFeeStrategy {
    private static final double PER_KM_RATE = 5.0;

    @Override
    public double calculateFee(double distance) {
        double fee = distance * PER_KM_RATE;
        System.out.println("[Standard Delivery] Fee for " + distance + " km = Rs " + fee);
        return fee;
    }
}
