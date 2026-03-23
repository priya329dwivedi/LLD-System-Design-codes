package org.designpattern.practiceQuestions.LeaderboardSystem.ObservorPattern;

public class SMS implements Observor{
    String mobileNumber;
    public SMS(String mobileNumber){
        if (mobileNumber == null || !mobileNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number: " + mobileNumber);
        }
        this.mobileNumber=mobileNumber;
    }
    @Override
    public void sendMsg(String msg) {
        System.out.println("SMS sent on phone "+this.mobileNumber+" :" + msg);
    }
}
