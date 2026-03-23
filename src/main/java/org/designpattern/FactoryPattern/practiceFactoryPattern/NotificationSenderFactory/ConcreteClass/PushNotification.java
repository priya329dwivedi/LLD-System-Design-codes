/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.FactoryPattern.practiceFactoryPattern.NotificationSenderFactory.ConcreteClass;

import org.designpattern.FactoryPattern.practiceFactoryPattern.NotificationSenderFactory.NotificationTypes;

public class PushNotification implements NotificationTypes {
    @Override
    public void sendMsg(String msg) {
        System.out.println("msg send via Push Notification "+ msg);
    }
}
