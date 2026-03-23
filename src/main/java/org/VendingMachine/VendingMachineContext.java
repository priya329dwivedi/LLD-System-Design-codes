package org.VendingMachine;

import lombok.Getter;
import lombok.Setter;
import org.VendingMachine.ConcreteClass.Inventory;
import org.VendingMachine.ConcreteClass.Item;
import org.VendingMachine.State.DispenseState;
import org.VendingMachine.State.HasIdleState;
import org.VendingMachine.State.HasMoneyState;
import org.VendingMachine.State.SelectionState;
import org.VendingMachine.enums.Coin;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class VendingMachineContext {
     private VendingSate state;
     private Inventory inventory;
     List<Coin> coinList;
     int selectedItemCode;

     public VendingMachineContext() {
         coinList = new ArrayList<>();
         this.state = new HasIdleState();
         inventory = new Inventory(10);
         System.out.println("Initialized: " + state.getState());
     }

     public void advanceState(){
         VendingSate nextState = state.next(this);
         if(nextState != null) {
             state = nextState;
             System.out.println("State: " + state.getState());
         }
     }

     public void clickOnInsertCoinButton(Coin coin) throws Exception {
         if(state instanceof HasMoneyState || state instanceof HasIdleState) {
             coinList.add(coin);
             advanceState();
         } else {
             throw new Exception("Vending Machine is not in idle state");
         }
     }

     public void clickOnStartProductSelectionButton(int codeNUmber) {
         if (state instanceof HasMoneyState) {
             try {
                 inventory.getItemFromInventory(codeNUmber);
                 advanceState();
                 selectProduct(codeNUmber);
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
         }
     }

    private void selectProduct(int codeNumber) throws Exception {
         if(state instanceof SelectionState) {
             Item item = inventory.getItemFromInventory(codeNumber);
             int price = item.getPrice();
             int balance = getBalance();
             if(price>balance){
                 System.out.println("Insufficient amount. Product price: " + item.getPrice() + ", paid: " + balance);
             }
             setSelectedItemCode(codeNumber);
             advanceState();
             dispenseSate(codeNumber);
             if(balance>price){
                 int change = balance-price;
                 System.out.println("change = " + change);
             }
         } else {
             throw new Exception("Vending Machine is not in idle state");
         }
    }

    private void dispenseSate(int codeNumber) {
         if(state instanceof DispenseState) {
             try {
                 inventory.removeItemFromInventory(inventory.getItemFromInventory(codeNumber), codeNumber);
                 advanceState();
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
         }
    }

    public int getBalance(){
         int balance =0;
         for(Coin coin : coinList){
             balance+= coin.value;
         }
         return balance;
    }
}
