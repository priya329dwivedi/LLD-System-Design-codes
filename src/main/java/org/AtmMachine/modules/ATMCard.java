package org.AtmMachine.modules;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ATMCard {
    int pin;
    int cardNUmber;
    int accountNumber;

    public ATMCard(int cardNUmber,int pin,int accountNumber){
        this.cardNUmber=cardNUmber;
        this.pin=pin;
        this.accountNumber=accountNumber;
    }

    public boolean validatePin(int pin){
        return this.pin==pin;
    }
}
