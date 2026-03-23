/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.RideBookingSystem.Observer;

import org.designpattern.practiceDesigns.RideBookingSystem.RideStatus;

public class BillingSystem implements NotificationObserver {
    @Override
    public void updatestatus(RideStatus ride) {
        System.out.println("Billing counter reached Status: "+ ride);
        if(ride.equals(RideStatus.RIDE_COMPLETED)){
            proceedPayment();
        }
    }

    private void proceedPayment() {
        System.out.println("Payment Completed!!!!!");
    }

}
