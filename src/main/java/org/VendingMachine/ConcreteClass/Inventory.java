package org.VendingMachine.ConcreteClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
    ItemSelf[] inventory = null;
    public Inventory(int itemCount){
        this.inventory = new ItemSelf[itemCount];
        initialEmptyInventory();
    }

    private void initialEmptyInventory() {
        int stateCode=101;
        for(int i=0;i<inventory.length;i++){
            ItemSelf itemSelf = new ItemSelf(stateCode++);
            inventory[i]=itemSelf;
        }
    }

    public void addItemToInventory(Item item, int code) throws Exception {
        if (code < 101 || code >= 101 + inventory.length) {
            throw new Exception("Invalid Code");
        }
        inventory[code - 101].addItem(item);
    }

    public void removeItemFromInventory(Item item, int code) throws Exception {
        if (code < 101 || code >= 101 + inventory.length) {
            throw new Exception("Invalid Code");
        }
        inventory[code - 101].removeItem(item);
        if (inventory[code - 101].getItemList().isEmpty()) {
            inventory[code - 101].setSoldOut(true);
        }
    }

    public Item getItemFromInventory(int code) throws Exception {
        if (code < 101 || code >= 101 + inventory.length) {
            throw new Exception("Invalid Code");
        }
        if (inventory[code - 101].getItemList().isEmpty()) {
            updateSoldOutItem(code);
            throw new Exception("Item is not available");
        }
        return inventory[code - 101].getItemList().get(0);
    }

    public void updateSoldOutItem(int code) throws Exception {
        if (code < 101 || code >= 101 + inventory.length) {
            throw new Exception("Invalid Code");
        }
        inventory[code - 101].setSoldOut(true);
    }

}
