package org.designpattern.StateDesignPattern.ConcretePattern;

import org.designpattern.StateDesignPattern.TrafficLight;
import org.designpattern.StateDesignPattern.TrafficLightContext;

public class GreenLight implements TrafficLight {
    @Override
    public void next(TrafficLightContext context) {
        System.out.println("Switching from green to yellow!!");
        context.setState(new YellowState());
    }

    @Override
    public void getColor() {
        System.out.println("Switching from green to yellow!!");
    }
}
