package com.rmart.orders.models;

import java.io.Serializable;

public class OrderObject implements Serializable {
    String name;
    String count;
    int bgTop;
    int bgBottom;

    public OrderObject(String name, String count, int bgTop, int bgBottom) {
        this.name = name;
        this.count = count;
        this.bgTop = bgTop;
        this.bgBottom = bgBottom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getBgTop() {
        return bgTop;
    }

    public void setBgTop(int bgTop) {
        this.bgTop = bgTop;
    }

    public int getBgBottom() {
        return bgBottom;
    }

    public void setBgBottom(int bgBottom) {
        this.bgBottom = bgBottom;
    }
}
