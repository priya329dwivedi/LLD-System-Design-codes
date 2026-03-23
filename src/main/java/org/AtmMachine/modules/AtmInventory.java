package org.AtmMachine.modules;

import org.AtmMachine.enums.Notes;

import java.util.LinkedHashMap;
import java.util.Map;

public class AtmInventory {
    Map<Notes,Integer> atmInventory = new LinkedHashMap<>();

    public AtmInventory(){
        initialiseInventory();
    }

    private void initialiseInventory() {
        atmInventory.put(Notes.NOTE_100,10);
        atmInventory.put(Notes.NOTE_20,10);
        atmInventory.put(Notes.NOTE_5, 100);
        atmInventory.put(Notes.NOTE_1,100);
    }

    public int getBalance(){
        int balance = 0;
        for(Map.Entry<Notes,Integer> entry : atmInventory.entrySet()){
            balance += entry.getKey().value * entry.getValue();
        }
        System.out.println("ATM Balance "+balance);
        return balance;
    }

    // Dispense cash
    public Map<Notes,Integer> withdrawCash(int cash){
        Map<Notes,Integer> withdrawlNotes = new LinkedHashMap<>();
        if(getBalance()>cash) return withdrawlNotes;
        for(Map.Entry<Notes, Integer> entry : atmInventory.entrySet()){
            int notes = cash/entry.getKey().value;
            if(notes > entry.getValue()){
                notes = entry.getValue();
                withdrawlNotes.put(entry.getKey(), notes);
                cash = cash - notes*entry.getKey().value;
            }
        }
        if(cash!=0){
            for(Map.Entry<Notes, Integer> entry : withdrawlNotes.entrySet()){
                atmInventory.put(entry.getKey(), atmInventory.get(entry.getKey())+entry.getValue());
            }
            return null;
        }
        return withdrawlNotes;
    }

    public void addCash(Map<Notes,Integer> cash){
        for(Map.Entry<Notes, Integer> entry : cash.entrySet()){
            atmInventory.put(entry.getKey(), atmInventory.get(entry.getKey())+entry.getValue());
        }
    }
}
