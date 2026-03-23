package org.VendingMachine.enums;

public enum ItemType {
    COKE(1),
    PEPSI(2),
    SODA(3);

    private int value;

    ItemType(int value) {
        this.value = value;
    }
}
