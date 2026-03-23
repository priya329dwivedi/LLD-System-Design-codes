package org.designpattern.StateDesignPattern.ConcretePattern;

import org.designpattern.StateDesignPattern.TrafficLight;
import org.designpattern.StateDesignPattern.TrafficLightContext;

public class RedLight implements TrafficLight {
    @Override
    public void next(TrafficLightContext context) {
        System.out.println("Switching from red to green!!");
        context.setState(new GreenLight());
    }

    @Override
    public void getColor() {
        System.out.println("Switching from red to green!!");
    }
}
