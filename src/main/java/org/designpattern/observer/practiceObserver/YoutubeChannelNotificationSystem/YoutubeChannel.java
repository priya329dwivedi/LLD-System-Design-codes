/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.observer.practiceObserver.YoutubeChannelNotificationSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class YoutubeChannel implements YoutubeChannelInterface{
    List<Notification> observers;
    String name;
    UUID id;
    List<String> videos;

    public  YoutubeChannel(String name){
        this.observers= new ArrayList<>();
        this.videos= new ArrayList<>();
        this.name=name;
        this.id= UUID.randomUUID();
    }
    @Override
    public void addSubscriber(Notification observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Notification observer) {
        observers.remove(observer);
    }

    @Override
    public void uploadNewVideo(String video) {
        videos.add(video);
        for(int i=0;i< observers.size();i++){
            observers.get(i).upload(video);
        }
    }
}
