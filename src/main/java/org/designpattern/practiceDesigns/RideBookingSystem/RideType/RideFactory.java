/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.RideBookingSystem.RideType;

public class RideFactory {
    public static RideTypes getRideObject(String type){
        if(type.equals("mini")){
            return new Mini();
        }
        else if(type.equals("sedan")){
            return new Sedan();
        }
        else if(type.equals("suv")){
            return  new SUV();
        }
        else {
            throw new IllegalArgumentException("Unavailable Ride Type");
        }
    }
}
