package org.designpattern.practiceDesigns.RideBookingSystem.PricingStrategyInterface;

public class NormalPrice implements PricingStrategy {

    public NormalPrice(){
    }
    @Override
    public double calculatePrice(double price) {
        return price ;
    }
}
