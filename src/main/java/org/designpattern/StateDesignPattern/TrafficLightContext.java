package org.designpattern.StateDesignPattern;

import org.designpattern.StateDesignPattern.ConcretePattern.RedLight;

public class TrafficLightContext {
    private TrafficLight currentState;

    public TrafficLightContext(){
        this.currentState= new RedLight();
    }

    public void next(){
        currentState.next(this);
    }

    public void setState(TrafficLight nextState){
        this.currentState=nextState;
    }

    public void getColor(){
        currentState.getColor();
    }

}
