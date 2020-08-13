package com.rmart.orders.models;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderListObject implements Serializable {
    String date;
    String orderID;
    ArrayList<ProductObject> productObjects;
    public OrderListObject(String date, String orderID, ArrayList<ProductObject> productObjects) {
        this.date = date;
        this.orderID = orderID;
        this.productObjects = productObjects;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return productObjects.size()+"";
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public ArrayList<ProductObject> getProductObjects() {
        return productObjects;
    }

    public void setProductObjects(ArrayList<ProductObject> productObjects) {
        this.productObjects = productObjects;
    }
}
