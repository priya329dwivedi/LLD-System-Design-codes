/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.RideBookingSystem;

import lombok.Getter;
import org.designpattern.practiceDesigns.RideBookingSystem.Observer.NotificationObserver;
import org.designpattern.practiceDesigns.RideBookingSystem.PricingStrategyInterface.PricingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ride {
    UUID id;
    double price;
    @Getter
    RideStatus status;
    List<NotificationObserver> notificationObservers;
    PricingStrategy strategy;
    public Ride(PricingStrategy strategy, double price){
        this.id= UUID.randomUUID();
        this.status= RideStatus.RIDE_REQUESTED;
        this.price=price;
        this.notificationObservers=new ArrayList<>();
        this.strategy=strategy;

    }
    public void addSubscriber(NotificationObserver strategy){
        notificationObservers.add(strategy);
    }
    public void removeSubscriber(NotificationObserver strategy){
        notificationObservers.remove(strategy);
    }
    public void notifyStatus(){
        for(NotificationObserver observer: notificationObservers){
            observer.updatestatus(status);
        }
    }
    public void setStatus(RideStatus status){
        this.status=status;
        notifyStatus();
    }

    public double calculateFinalPrice() {
        return this.strategy.calculatePrice(this.price);
    }
}
