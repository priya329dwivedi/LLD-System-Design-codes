package org.VendingMachine.State;

import org.VendingMachine.VendingMachineContext;
import org.VendingMachine.VendingSate;

public class HasMoneyState implements VendingSate {
    @Override
    public String getState() {
        return "Has Money State";
    }

    @Override
    public VendingSate next(VendingMachineContext context) {
        return new SelectionState();
    }
}
