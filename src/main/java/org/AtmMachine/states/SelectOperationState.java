package org.AtmMachine.states;

import org.AtmMachine.ATMMachineContext;
import org.AtmMachine.ATMState;

public class SelectOperationState implements ATMState {
    @Override
    public String getSate() {
        return "Select Operation State";
    }

    @Override
    public ATMState next(ATMMachineContext context) {
        return new TransactionState();
    }
}
