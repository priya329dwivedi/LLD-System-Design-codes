package org.VendingMachine;

public interface VendingSate {
    String getState();

    VendingSate next(VendingMachineContext vendingMachineContext);
}
