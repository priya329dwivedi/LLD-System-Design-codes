package org.AtmMachine.modules;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {
    int balance;
    int accountNumber;

    public Account(int accountNumber,int balance){
        this.accountNumber=accountNumber;
        this.balance=balance;
    }
    public boolean withdraw(int amount){
        if(balance<amount){
            return false;
        }
        balance-=amount;
        return true;
    }
    public void deposit(int amount){
        balance+=amount;
    }

}
