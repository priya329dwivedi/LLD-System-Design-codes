package org.AtmMachine;

import lombok.Getter;
import org.AtmMachine.enums.Operation;
import org.AtmMachine.modules.ATMCard;
import org.AtmMachine.modules.Account;
import org.AtmMachine.modules.AtmInventory;
import org.AtmMachine.states.HasCardState;
import org.AtmMachine.states.IdleState;
import org.AtmMachine.states.SelectOperationState;
import org.AtmMachine.states.TransactionState;

import java.util.HashMap;
import java.util.Map;
@Getter
public class ATMMachineContext {
    private static ATMMachineContext context=null;
    private ATMState currentState;
    private ATMCard currentCard;
    private Account currentAccount;
    private Map<Integer, Account> accounts;
    private AtmInventory inventory;
    private Operation selectedOperation;
    private ATMMachineContext (){
        this.currentState= new IdleState();
        this.inventory= new AtmInventory();
        this.accounts= new HashMap<>();
        this.currentAccount= null;
    }
    public static ATMMachineContext getInstance(){
        if(context==null){
            return new ATMMachineContext();
        }
        return context;
    }

    private void resetAtm() {
        this.currentState= new IdleState();
        this.currentAccount=null;
        this.selectedOperation=null;
        this.currentCard=null;
        context=null;
    }

    private void advanceSate(){
        ATMState atmState= currentState.next(this);
        currentState= atmState;
        System.out.println("Current State: "+currentState.getSate());
    }
    public void insertCard(ATMCard card){
        if(currentState instanceof IdleState){
            System.out.println("ATM Card inserted with number " + card.getCardNUmber()+ " with account number " + card.getAccountNumber());
            currentCard= card;
            advanceSate();
        }else{
            System.out.println("Cannot insert card" + currentState.getSate());
        }
    }
    public void enterPin(int pin){
        if(currentState instanceof HasCardState){
            System.out.println("enter Pin "+ pin);
            if(currentCard.validatePin(pin)){
                currentAccount = accounts.get(currentCard.getAccountNumber());
                advanceSate();
            }else{
                System.out.println("Invalid Pin");
            }
        }
    }
    public void selectOperationState(Operation operation) {
        if(currentState instanceof SelectOperationState){
            System.out.println("Select out of these WITHDRAW, DEPOSIT, CHECK_BALANCE");
            System.out.println("Selected operation " + operation);
            this.selectedOperation=operation;
            advanceSate();
        }
        else{
            System.out.println("Cannot select operation" + currentState.getSate());
        }
    }

    public void performingWithdrawl(int amount){
        if(currentState instanceof TransactionState){
            System.out.println("Performing withdrawl of amount "+ amount);
            switch(selectedOperation){
                case WITHDRAW :
                    withdrawCash(amount);
                case DEPOSIT :
                    depositCash(amount);
                case CHECK_BALANCE:
                    System.out.println("Current account balance "+ currentAccount.getBalance());
            }
            advanceSate();
        }
        else{
            System.out.println("Cannot perform operation" + currentState.getSate());
        }
    }

    public void returnCard(){
        if(currentState instanceof TransactionState
                || currentState instanceof SelectOperationState
                || currentState instanceof HasCardState){
            System.out.println("Returning card");
            resetAtm();
            advanceSate();
        }
    }

    public void addAccount(Account account){
        accounts.put((account.getAccountNumber()),account);
    }

    private void depositCash(int amount) {
        if(currentAccount==null){
            currentAccount= new Account(currentCard.getAccountNumber(),0);
        }
        currentAccount.deposit(amount);
    }

    private void withdrawCash(int amount) {
        if(inventory.getBalance()<amount){
            System.out.println("Insufficient cash in machine");
            advanceSate();
        }
        else{
            if(currentAccount.getBalance()>amount){
                currentAccount.withdraw(amount);
                inventory.withdrawCash(amount);
                System.out.println("Current account balance "+ currentAccount.getBalance());
                advanceSate();
            }
        }
    }
}
