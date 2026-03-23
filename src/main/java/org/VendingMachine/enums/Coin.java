package org.VendingMachine.enums;

public enum Coin {
    FIVE_RUPEES(5),
    TEN_RUPEES(10),
    TWENTY_RUPEES(20);

    public int value;

    Coin(int value) {
        this.value = value;
    }
}
