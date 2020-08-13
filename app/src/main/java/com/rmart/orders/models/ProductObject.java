package com.rmart.orders.models;

import java.io.Serializable;

public class ProductObject implements Serializable {
    public String productName, quantity, units, price;
    public ProductObject(String productName, String quantity, String units, String price) {
        this.productName = productName;
        this.quantity = quantity;
        this.units = units;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
