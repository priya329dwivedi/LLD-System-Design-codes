/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.RideBookingSystem.Observer;

import org.designpattern.practiceDesigns.RideBookingSystem.RideStatus;

public class RideNotification implements NotificationObserver {
    @Override
    public void updatestatus(RideStatus ride) {
        System.out.println("RIDE NOTIFICATION Status: "+ ride);
    }
}
