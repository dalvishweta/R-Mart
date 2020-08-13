package com.rmart.orders.models;

import com.rmart.inventory.adapters.ProductAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderListObject implements Serializable {
    String date;
    String count;
    String orderID;
    ArrayList<ProductObject> productObjects;
    public OrderListObject(String date, String count, String orderID, ArrayList<ProductObject> productObjects) {
        this.date = date;
        this.count = count;
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
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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
