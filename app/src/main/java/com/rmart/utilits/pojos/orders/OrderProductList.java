package com.rmart.utilits.pojos.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderProductList implements Serializable {
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("mode_of_payment")
    @Expose
    private String mode_of_payment;

    @SerializedName("total_amt")
    @Expose
    private String total_amt;


    @SerializedName("products")
    @Expose
    ArrayList<Product> products;

    // need to add these parameters

    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges;

    @SerializedName("product_amount")
    @Expose
    private String productAmount;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }
}
