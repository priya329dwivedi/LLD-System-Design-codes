package org.NotificationService.src;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClientService {
    private String clientName;
    List<Subscriber> subscribers;

    public ClientService(String name){
        this.clientName=name;
        this.subscribers=new ArrayList<>();
    }

    public void add(Subscriber subscriber){
        subscribers.add(subscriber);
    }
    public void publish(NotificationSeverity severity,String msg){
        for(Subscriber subscriber: subscribers){
            subscriber.notify(severity,msg);
        }
    }
}
