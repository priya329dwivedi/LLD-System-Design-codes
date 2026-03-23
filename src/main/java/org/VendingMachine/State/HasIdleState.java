package org.VendingMachine.State;

import org.VendingMachine.VendingMachineContext;
import org.VendingMachine.VendingSate;

public class HasIdleState implements VendingSate {
    @Override
    public String getState() {
        return "Idle State";
    }

    @Override
    public VendingSate next(VendingMachineContext context) {
        return new HasMoneyState();
    }
}
