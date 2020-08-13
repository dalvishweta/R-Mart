package com.rmart.orders.models;

import java.io.Serializable;
import java.util.ArrayList;

public class OrdersByType implements Serializable {
    String orderType;
    String count;
    int bgTop;
    int bgBottom;
    ArrayList<OrderListObject> orderListObjects;

    public OrdersByType(String orderType, int bgTop, int bgBottom, ArrayList<OrderListObject> orderListObjects) {

        this.orderType = orderType;
        this.count = orderListObjects.size()+"";
        this.bgTop = bgTop;
        this.bgBottom = bgBottom;
        this.orderListObjects = orderListObjects;
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

    public ArrayList<OrderListObject> getOrderListObjects() {
        return orderListObjects;
    }

    public void setOrderListObjects(ArrayList<OrderListObject> orderListObjects) {
        this.orderListObjects = orderListObjects;
    }
}
