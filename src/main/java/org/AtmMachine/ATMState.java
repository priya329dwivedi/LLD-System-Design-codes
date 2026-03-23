package org.AtmMachine;

public interface ATMState {
    public String getSate();
    public ATMState next(ATMMachineContext context);

}
