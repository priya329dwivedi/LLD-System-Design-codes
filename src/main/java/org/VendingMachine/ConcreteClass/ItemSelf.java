package org.VendingMachine.ConcreteClass;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemSelf {
    private List<Item> itemList;
    private int ItemCode;
    private boolean isSoldOut;

    public ItemSelf(int itemCode) {
        ItemCode = itemCode;
        this.itemList = new ArrayList<>();
        isSoldOut=false;
    }
    public void addItem(Item item) {
        this.itemList.add(item);
        isSoldOut = false;
    }
    public void removeItem(Item item) {
        this.itemList.remove(item);
        if(itemList.isEmpty()) isSoldOut=true;
    }
}
