/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery;

public class FreeDelivery implements DeliveryFeeStrategy {
    @Override
    public double calculateFee(double distance) {
        System.out.println("[Free Delivery] Premium member - no delivery charge");
        return 0;
    }
}
