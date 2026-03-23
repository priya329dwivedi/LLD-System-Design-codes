/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.RideBookingSystem.RideType;

public class SUV implements RideTypes {
    int ratePerKm=15;
    public SUV(){
    }
    @Override
    public double calculateRide(double distance) {
        return distance*ratePerKm;
    }
}
