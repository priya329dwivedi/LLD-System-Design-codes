package org.NotificationService.src.Devices;

public class Email implements Device{
    @Override
    public void update(String msg) {
        System.out.println(msg+ " -message via Email");
    }
}
