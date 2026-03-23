package org.AtmMachine.states;

import org.AtmMachine.ATMMachineContext;
import org.AtmMachine.ATMState;

public class HasCardState implements ATMState {
    @Override
    public String getSate() {
        return "Has Card State";
    }

    @Override
    public ATMState next(ATMMachineContext context) {
        return new SelectOperationState();
    }
}
