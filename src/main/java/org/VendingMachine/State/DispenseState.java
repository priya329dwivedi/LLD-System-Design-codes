package org.VendingMachine.State;

import org.VendingMachine.VendingMachineContext;
import org.VendingMachine.VendingSate;

public class DispenseState implements VendingSate {
    @Override
    public String getState() {
        return "Dispense State";
    }

    @Override
    public VendingSate next(VendingMachineContext context) {
        return new HasIdleState();
    }
}
