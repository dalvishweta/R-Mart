package com.rmart.orders.models;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectedOrderGroup implements Serializable {
    String orderType;
    String count;
    int bgTop;
    int bgBottom;
    ArrayList<OrderObject> orderObjects;

    public SelectedOrderGroup(String orderType, int bgTop, int bgBottom, ArrayList<OrderObject> orderObjects) {

        this.orderType = orderType;
        this.count = orderObjects.size()+"";
        this.bgTop = bgTop;
        this.bgBottom = bgBottom;
        this.orderObjects = orderObjects;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public ArrayList<OrderObject> getOrderObjects() {
        return orderObjects;
    }

    public void setOrderObjects(ArrayList<OrderObject> orderObjects) {
        this.orderObjects = orderObjects;
    }
    public void setOrderObjects(OrderObject orderObject) {
        this.orderObjects.add(orderObject);
        count = orderObjects.size()+"";
    }
    public void removeOrderObjects(OrderObject orderObject) {
        this.orderObjects.remove(orderObject);
        count = orderObjects.size()+"";
    }
}
