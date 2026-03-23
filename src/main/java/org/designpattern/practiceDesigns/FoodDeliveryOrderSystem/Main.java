/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem;

import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery.ExpressDelivery;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery.FreeDelivery;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery.StandardDelivery;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery.SurgePricing;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.observer.AnalyticsSystem;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.observer.DeliveryTrackingSystem;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.observer.NotificationSystem;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.payment.PaymentFactory;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.payment.PaymentMethod;

public class Main {
    public static void main(String[] args) {
        // Observer: create systems that react to order status changes
        NotificationSystem notificationSystem = new NotificationSystem();
        DeliveryTrackingSystem trackingSystem = new DeliveryTrackingSystem();
        AnalyticsSystem analyticsSystem = new AnalyticsSystem();

        // Order 1: Standard delivery, pay with UPI (sufficient balance)
        System.out.println("========== ORDER 1: Successful Payment ==========");
        Order order1 = new Order(450, 5, new StandardDelivery());
        order1.addObserver(notificationSystem);
        order1.addObserver(trackingSystem);
        order1.addObserver(analyticsSystem);
        PaymentMethod upi = PaymentFactory.getPaymentMethod("upi", 1000);
        order1.checkout(upi);
        order1.updateStatus("Preparing");
        order1.updateStatus("Out for Delivery");
        order1.updateStatus("Delivered");
        order1.shutdown();

        // Order 2: Payment failure demo (wallet has insufficient balance)
        System.out.println("\n========== ORDER 2: Payment Failure ==========");
        Order order2 = new Order(800, 8, new ExpressDelivery());
        order2.addObserver(notificationSystem);
        order2.addObserver(trackingSystem);
        PaymentMethod wallet = PaymentFactory.getPaymentMethod("wallet", 100);
        boolean success = order2.checkout(wallet);
        if(!success){
            System.out.println("Retrying with Credit Card...");
            PaymentMethod creditCard = PaymentFactory.getPaymentMethod("creditcard", 5000);
            order2.checkout(creditCard);
        }
        order2.updateStatus("Preparing");

        // Strategy: switch to surge pricing mid-flow (peak hour started)
        order2.setDeliveryFeeStrategy(new SurgePricing(2.5));
        System.out.println("\nRecalculated total with surge: Rs " + order2.getTotalAmount());
        order2.updateStatus("Out for Delivery");
        order2.updateStatus("Delivered");
        order2.shutdown();

        // Order 3: New delivery model - FreeDelivery for premium members
        System.out.println("\n========== ORDER 3: Premium Member (Free Delivery) ==========");
        Order order3 = new Order(600, 10, new FreeDelivery());
        order3.addObserver(notificationSystem);
        // Dynamically add more observers at runtime
        order3.addObserver(analyticsSystem);
        PaymentMethod premiumUpi = PaymentFactory.getPaymentMethod("upi", 2000);
        order3.checkout(premiumUpi);
        // Dynamically add tracking observer mid-flow
        order3.addObserver(trackingSystem);
        order3.updateStatus("Preparing");
        order3.updateStatus("Out for Delivery");
        // Dynamically remove analytics before delivery
        order3.removeObserver(analyticsSystem);
        order3.updateStatus("Delivered");
        order3.shutdown();
    }
}
