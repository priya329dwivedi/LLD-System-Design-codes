/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.FoodDeliveryOrderSystem;

import lombok.Getter;
import lombok.Setter;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.delivery.DeliveryFeeStrategy;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.observer.OrderObserver;
import org.designpattern.practiceDesigns.FoodDeliveryOrderSystem.payment.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Order {
    @Getter
    String orderId;
    String status;
    double foodTotal;
    double distance;
    @Setter
    DeliveryFeeStrategy deliveryFeeStrategy;
    List<OrderObserver> observers;
    ExecutorService executorService;

    public Order(double foodTotal, double distance, DeliveryFeeStrategy deliveryFeeStrategy) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8);
        this.foodTotal = foodTotal;
        this.distance = distance;
        this.deliveryFeeStrategy = deliveryFeeStrategy;
        this.observers = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(3);
        this.status = "Created";
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for(OrderObserver observer : observers){
            executorService.submit(() -> observer.update(orderId, status));
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("\n--- Order " + orderId + " status changed to: " + newStatus + " ---");
        notifyObservers();
    }

    public double getTotalAmount() {
        double deliveryFee = deliveryFeeStrategy.calculateFee(distance);
        return foodTotal + deliveryFee;
    }

    public boolean checkout(PaymentMethod paymentMethod) {
        double total = getTotalAmount();
        System.out.println("Food total: Rs " + foodTotal + " + Delivery fee = Rs " + total);
        boolean success = paymentMethod.pay(total);
        if(success){
            updateStatus("Order Placed");
        }
        else{
            updateStatus("Payment Failed");
        }
        return success;
    }
}
