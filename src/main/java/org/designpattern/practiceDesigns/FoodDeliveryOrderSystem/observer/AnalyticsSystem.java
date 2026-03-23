/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.observer;

public class AnalyticsSystem implements OrderObserver {
    @Override
    public void update(String orderId, String status) {
        System.out.println("[Analytics] Order " + orderId + " logged: " + status);
    }
}
