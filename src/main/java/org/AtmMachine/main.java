package org.AtmMachine;

import org.AtmMachine.enums.Operation;
import org.AtmMachine.modules.ATMCard;
import org.AtmMachine.modules.Account;

public class main {
    public static void main(String[] args) {
        Account account1= new Account(1234,1000);
        Account account2= new Account(1235, 2000);
        Account account3= new Account(1236, 3000);
        ATMCard atmCard1= new ATMCard(5555,4321,1234);
        ATMCard atmCard2= new ATMCard(5556, 5321, 1235);
        ATMCard atmCard3= new ATMCard(5557, 6321, 1236);
        ATMMachineContext atmMachineContext= ATMMachineContext.getInstance();
        try {
            atmMachineContext.addAccount(account1);
            atmMachineContext.addAccount(account2);
            atmMachineContext.addAccount(account3);
            atmMachineContext.insertCard(atmCard1);
            atmMachineContext.enterPin(4321);
            atmMachineContext.selectOperationState(Operation.CHECK_BALANCE);
            atmMachineContext.performingWithdrawl(0);
            atmMachineContext.selectOperationState(Operation.WITHDRAW);
            atmMachineContext.performingWithdrawl(100);
            atmMachineContext.returnCard();
            System.out.println("|||||||| Transaction 2 |||||||");
            atmMachineContext.insertCard(atmCard2);
            atmMachineContext.enterPin(5321);
            atmMachineContext.selectOperationState(Operation.CHECK_BALANCE);
            atmMachineContext.performingWithdrawl(0);
            atmMachineContext.selectOperationState(Operation.WITHDRAW);
            atmMachineContext.performingWithdrawl(100);
            System.out.println("Atm withdrawn Demo completed");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
