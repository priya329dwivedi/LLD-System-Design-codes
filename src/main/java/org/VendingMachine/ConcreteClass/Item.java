package org.VendingMachine.ConcreteClass;

import lombok.Getter;
import lombok.Setter;
import org.VendingMachine.enums.ItemType;

@Getter
@Setter
public class Item {
    private int price;
    private ItemType itemType;
}
