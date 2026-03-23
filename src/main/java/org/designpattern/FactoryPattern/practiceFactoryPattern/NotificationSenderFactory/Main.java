/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.FactoryPattern.practiceFactoryPattern.NotificationSenderFactory;

public class Main {
    public static void main(String[] args) {
        NotificationTypes notificationTypes= NotificationTypesFactory.getObject("sms");
        notificationTypes.sendMsg("Hello world!!");
        NotificationTypes notificationTypes1= NotificationTypesFactory.getObject("email");
        notificationTypes1.sendMsg("Hello World!!");
        NotificationTypes notificationTypes2= NotificationTypesFactory.getObject("push");
        notificationTypes2.sendMsg("Pushing into life");
    }
}
