package org.AtmMachine.states;

import org.AtmMachine.ATMMachineContext;
import org.AtmMachine.ATMState;

public class TransactionState implements ATMState {
    @Override
    public String getSate() {
        return "Transaction State";
    }

    @Override
    public ATMState next(ATMMachineContext context) {
        if (context.getCurrentCard() != null) {
            return new SelectOperationState();
        }
        return new IdleState();
    }
}
