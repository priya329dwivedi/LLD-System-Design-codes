/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem.ConcreteClass;

import org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem.Notification;
import org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem.User;

public class InApp implements Notification {
    User user;

    public InApp(User user) {
        this.user = user;
    }

    @Override
    public void upload(String file) {
        System.out.println("[InApp] " + user.getName() + " (device: " + user.getDeviceId() + ") notified: new video - " + file);
    }
}
