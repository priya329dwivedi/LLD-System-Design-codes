/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem;

import org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem.ConcreteClass.Email;
import org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem.ConcreteClass.InApp;
import org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem.ConcreteClass.PushNotification;

public class Main {
    public static void main(String[] args) {
        User priya = new User("Priya", "priya@gmail.com", "+91-9876543210", "device-001");
        User rahul = new User("Rahul", "rahul@gmail.com", "+91-9876543211", "device-002");
        User amit = new User("Amit", "amit@gmail.com", "+91-9876543212", "device-003");
        Email email = new Email(priya);
        InApp inApp = new InApp(rahul);
        PushNotification pushNotification = new PushNotification(amit);
        YoutubeChannel youtubeChannel= new YoutubeChannel("champu ki dukan");
        youtubeChannel.addSubscriber(email);
        youtubeChannel.addSubscriber(inApp);
        youtubeChannel.uploadNewVideo("champu ki massage");
        youtubeChannel.unsubscribe(inApp);
        youtubeChannel.addSubscriber(pushNotification);
        youtubeChannel.uploadNewVideo("champu ki lassi!!!");
    }
}
