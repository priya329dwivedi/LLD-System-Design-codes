package org.AtmMachine.enums;

public enum Notes {
    NOTE_100(100),
    NOTE_20(20),
    NOTE_5(5),
    NOTE_1(1);
    public final int value;
    Notes(int value) {
        this.value = value;
    }
}
