/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.payment;

public class UPI implements PaymentMethod {
    private double balance;

    public UPI(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if(amount > balance){
            System.out.println("[UPI] Payment of Rs " + amount + " FAILED - insufficient balance (Rs " + balance + ")");
            return false;
        }
        balance -= amount;
        System.out.println("[UPI] Payment of Rs " + amount + " successful");
        return true;
    }
}
