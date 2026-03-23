package org.NotificationService.src.Devices;

public class InApp implements Device{
    @Override
    public void update(String msg) {
        System.out.println(msg+" -message via In-App notification");
    }
}
