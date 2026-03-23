package org.AtmMachine.states;

import org.AtmMachine.ATMMachineContext;
import org.AtmMachine.ATMState;

public class IdleState implements ATMState {
    @Override
    public String getSate() {
        return "Idle State";
    }

    @Override
    public ATMState next(ATMMachineContext context) {
        return new HasCardState();
    }
}
