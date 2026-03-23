package org.designpattern.practiceQuestions.LeaderboardSystem.ObservorPattern;

public class InAppNotification implements Observor{
    String deviceId;
    public InAppNotification(String deviceId){
        this.deviceId=deviceId;
    }

    @Override
    public void sendMsg(String msg) {
        System.out.println("In-app Notification "+this.deviceId+" :" + msg);
    }
}
