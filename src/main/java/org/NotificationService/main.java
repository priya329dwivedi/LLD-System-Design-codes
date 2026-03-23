package org.NotificationService;

import org.NotificationService.src.ClientService;
import org.NotificationService.src.Devices.Device;
import org.NotificationService.src.Devices.Email;
import org.NotificationService.src.Devices.InApp;
import org.NotificationService.src.Devices.Phone;
import org.NotificationService.src.NotificationSeverity;
import org.NotificationService.src.Subscriber;

import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        Device email = new Email();
        Device phone = new Phone();
        Device inApp = new InApp();
        ClientService aws= new ClientService("AWS");
        Subscriber sub_1= new Subscriber();
        sub_1.setStrategy(NotificationSeverity.HIGH, Arrays.asList(email,phone,inApp));
        sub_1.setStrategy(NotificationSeverity.MEDIUM,Arrays.asList(phone,inApp));
        sub_1.setStrategy(NotificationSeverity.LOW,Arrays.asList(inApp));
        aws.add(sub_1);
        aws.publish(NotificationSeverity.HIGH,"AWS stock unavailable");
    }
}
