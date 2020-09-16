package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 16/09/20.
 */
public class ShoppingCartResponseDetails implements Serializable {

    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("cart_price")
    @Expose
    private Integer cartPrice;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(Integer cartPrice) {
        this.cartPrice = cartPrice;
    }
}
