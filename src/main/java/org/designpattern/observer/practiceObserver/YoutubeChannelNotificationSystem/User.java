/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem;

import lombok.Getter;

import java.util.UUID;

@Getter
public class User {
    UUID id;
    String name;
    String email;

    String phoneNumber;
    String deviceId;
    public User(String name, String email, String phoneNumber, String deviceId){
        this.id= UUID.randomUUID();
        this.name=name;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.deviceId=deviceId;
    }
}
