/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.RideBookingSystem;

public class Driver {
    String name;
    boolean available;

    public Driver(String name) {
        this.name = name;
        this.available = true;
    }

    public boolean acceptRide(Ride ride) {
        if(available){
            System.out.println("Driver " + name + " accepted the ride");
            this.available = false;
            ride.setStatus(RideStatus.DRIVER_ASSIGNED);
            return true;
        }
        System.out.println("Driver " + name + " is not available");
        return false;
    }

    public void rejectRide(Ride ride) {
        System.out.println("Driver " + name + " rejected the ride");
    }

    public void completeRide(Ride ride) {
        System.out.println("Driver " + name + " completed the ride");
        ride.setStatus(RideStatus.RIDE_COMPLETED);
        this.available = true;
    }

    public String getName() {
        return name;
    }
}
