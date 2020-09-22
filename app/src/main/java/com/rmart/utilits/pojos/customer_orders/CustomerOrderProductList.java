package com.rmart.utilits.pojos.customer_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.orders.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerOrderProductList implements Serializable {

    @SerializedName("customer_info")
    @Expose
    CustomerInfo customerInfo;

    @SerializedName("order_info")
    @Expose
    OrderInfo orderInfo;

    @SerializedName("vendurInfo")
    @Expose
    VendorInfo vendorInfo;

    @SerializedName("products")
    @Expose
    ArrayList<Product> product;

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public VendorInfo getVendorInfo() {
        return vendorInfo;
    }

    public void setVendorInfo(VendorInfo vendorInfo) {
        this.vendorInfo = vendorInfo;
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }
}
