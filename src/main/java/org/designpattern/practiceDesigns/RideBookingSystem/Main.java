/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.RideBookingSystem;

import org.designpattern.practiceDesigns.RideBookingSystem.Observer.BillingSystem;
import org.designpattern.practiceDesigns.RideBookingSystem.Observer.DriverApp;
import org.designpattern.practiceDesigns.RideBookingSystem.Observer.RideNotification;
import org.designpattern.practiceDesigns.RideBookingSystem.PricingStrategyInterface.NormalPrice;
import org.designpattern.practiceDesigns.RideBookingSystem.PricingStrategyInterface.SharedRidePrice;
import org.designpattern.practiceDesigns.RideBookingSystem.PricingStrategyInterface.SurgePrice;
import org.designpattern.practiceDesigns.RideBookingSystem.RideType.RideFactory;
import org.designpattern.practiceDesigns.RideBookingSystem.RideType.RideTypes;

public class Main {
    public static void main(String[] args) {
        // Observers
        BillingSystem billingSystem= new BillingSystem();
        DriverApp driverApp= new DriverApp();
        RideNotification rideNotification= new RideNotification();

        // Ride 1: Mini with Surge pricing, driver accept flow
        System.out.println("========== RIDE 1: Mini + Surge ==========");
        RideTypes miniRide= RideFactory.getRideObject("mini");
        SurgePrice surgePrice= new SurgePrice(1.5);
        Ride ride1 = new Ride(surgePrice, miniRide.calculateRide(20));
        ride1.addSubscriber(driverApp);
        ride1.addSubscriber(billingSystem);
        ride1.addSubscriber(rideNotification);
        System.out.println("Final Price of Ride:- " + ride1.calculateFinalPrice());
        ride1.notifyStatus();

        // Driver rejects, then another driver accepts
        Driver driver1 = new Driver("Rahul");
        driver1.rejectRide(ride1);
        Driver driver2 = new Driver("Amit");
        driver2.acceptRide(ride1);
        ride1.setStatus(RideStatus.RIDE_STARTED);
        driver2.completeRide(ride1);

        // Ride 2: SUV with Normal pricing
        System.out.println("\n========== RIDE 2: SUV + Normal ==========");
        RideTypes suvRide= RideFactory.getRideObject("suv");
        NormalPrice normalPrice= new NormalPrice();
        Ride ride2 = new Ride(normalPrice, suvRide.calculateRide(10));
        ride2.addSubscriber(driverApp);
        ride2.addSubscriber(billingSystem);
        System.out.println("Final Price of Ride:- " + ride2.calculateFinalPrice());
        ride2.notifyStatus();
        Driver driver3 = new Driver("Priya");
        driver3.acceptRide(ride2);
        ride2.setStatus(RideStatus.RIDE_STARTED);
        driver3.completeRide(ride2);

        // Ride 3: Sedan with Shared pricing (3 passengers)
        System.out.println("\n========== RIDE 3: Sedan + Shared (3 passengers) ==========");
        RideTypes sedanRide= RideFactory.getRideObject("sedan");
        SharedRidePrice sharedRidePrice= new SharedRidePrice(3);
        Ride ride3 = new Ride(sharedRidePrice, sedanRide.calculateRide(15));
        ride3.addSubscriber(rideNotification);
        ride3.addSubscriber(billingSystem);
        System.out.println("Price per passenger:- " + ride3.calculateFinalPrice());
        ride3.notifyStatus();
        Driver driver4 = new Driver("Vijay");
        driver4.acceptRide(ride3);
        ride3.setStatus(RideStatus.RIDE_STARTED);
        driver4.completeRide(ride3);
    }
}
