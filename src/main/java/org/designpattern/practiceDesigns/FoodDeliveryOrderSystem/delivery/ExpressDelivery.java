/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery;

public class ExpressDelivery implements DeliveryFeeStrategy {
    private static final double PER_KM_RATE = 10.0;
    private static final double EXPRESS_SURCHARGE = 30.0;

    @Override
    public double calculateFee(double distance) {
        double fee = (distance * PER_KM_RATE) + EXPRESS_SURCHARGE;
        System.out.println("[Express Delivery] Fee for " + distance + " km = Rs " + fee);
        return fee;
    }
}
