/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.payment;

public class PaymentFactory {
    public static PaymentMethod getPaymentMethod(String type, double balanceOrLimit){
        if(type.equals("creditcard")){
            return new CreditCard(balanceOrLimit);
        }
        else if(type.equals("upi")){
            return new UPI(balanceOrLimit);
        }
        else if(type.equals("wallet")){
            return new Wallet(balanceOrLimit);
        }
        else{
            throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }
}
