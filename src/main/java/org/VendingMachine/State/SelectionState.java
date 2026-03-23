package org.VendingMachine.State;

import org.VendingMachine.VendingMachineContext;
import org.VendingMachine.VendingSate;

public class SelectionState implements VendingSate {
    @Override
    public String getState() {
        return "SelectionState";
    }

    @Override
    public VendingSate next(VendingMachineContext context) {
        return new DispenseState();
    }
}
