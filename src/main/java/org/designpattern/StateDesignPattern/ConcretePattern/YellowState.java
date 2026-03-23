package org.designpattern.StateDesignPattern.ConcretePattern;

import org.designpattern.StateDesignPattern.TrafficLight;
import org.designpattern.StateDesignPattern.TrafficLightContext;

public class YellowState implements TrafficLight {
    @Override
    public void next(TrafficLightContext context) {
        System.out.println("Switching from yellow to Red!!");
        context.setState(new RedLight());
    }

    @Override
    public  void getColor() {
        System.out.println("Switching from yellow to Red!!");
    }
}
