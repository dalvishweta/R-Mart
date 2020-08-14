package com.rmart.orders.models;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderObject implements Serializable {
    String date;
    String orderID;
    String orderType;
    ArrayList<ProductObject> productObjects;
    String customerName;
    String customerNumber;
    String customerAddress;
    String totalAmount;
    String orderAmount;
    String modeType;
    String charges;
    boolean isDue;

    public OrderObject(String date, String orderID, ArrayList<ProductObject> productObjects, String orderType, boolean isDue) {
        this.date = date;
        this.orderID = orderID;
        this.orderType = orderType;
        this.productObjects = productObjects;

        this.customerName = orderID;
        this.customerNumber = "7416226233";
        this.customerAddress = "Vamshee Krishna, 18-1814, Sagar road, Hanuman pent, miryalguda, Nalgonda district, Telangana";
        this.totalAmount = "1000";
        this.orderAmount = "900";
        this.charges = "100";
        this.isDue = isDue;
        if(isDue) {
            modeType = "Net Banking";
        }else {
            modeType = "COD";
        }
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public boolean isDue() {
        return isDue;
    }

    public void setDue(boolean due) {
        isDue = due;
    }
}
