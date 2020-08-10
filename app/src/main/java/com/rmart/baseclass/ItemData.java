package com.rmart.baseclass;

import java.io.Serializable;

public class ItemData implements Serializable {

    String itemName;
    String itemKey;
    int index;

    public ItemData(String name, int id) {
        itemName = name;
        index = id;
    }
    public ItemData(String name, String key) {
        itemName = name;
        this.itemKey = itemKey;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

