package org.NotificationService.src;

import lombok.Setter;
import org.NotificationService.src.Devices.Device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subscriber {
    private String name;
    Map<NotificationSeverity,List<Device>> strategies= new HashMap<>();

    public void setStrategy(NotificationSeverity severity, List<Device> device){
        strategies.put(severity,device);
    }
    public void notify(NotificationSeverity severity, String msg){
        List<Device> devices= strategies.get(severity);
        for(Device device: devices){
            device.update("it is out of stock-" + msg);
        }
    }
}
