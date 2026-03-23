package org.NotificationService.src.Devices;

public class Phone implements Device {
    @Override
    public void update(String msg) {
        System.out.println(msg+ " message via phone");
    }
}
