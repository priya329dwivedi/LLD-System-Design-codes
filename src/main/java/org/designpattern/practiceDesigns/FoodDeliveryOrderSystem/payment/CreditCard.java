/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.payment;

public class CreditCard implements PaymentMethod {
    private double limit;

    public CreditCard(double limit) {
        this.limit = limit;
    }

    @Override
    public boolean pay(double amount) {
        if(amount > limit){
            System.out.println("[CreditCard] Payment of Rs " + amount + " FAILED - exceeds limit of Rs " + limit);
            return false;
        }
        System.out.println("[CreditCard] Payment of Rs " + amount + " successful");
        return true;
    }
}
