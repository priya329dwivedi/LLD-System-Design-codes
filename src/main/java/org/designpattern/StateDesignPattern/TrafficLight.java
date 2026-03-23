package org.designpattern.StateDesignPattern;

public interface TrafficLight {
    void next(TrafficLightContext context);
    void getColor();
}
