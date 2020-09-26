package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 19/09/20.
 */
public class ProductOrderedResponseDetails implements Serializable {

    @SerializedName("order_message")
    @Expose
    private String orderedMessage = null;

    @SerializedName("total_cart_count")
    @Expose
    private int totalCartCount = 0;

    public String getOrderedMessage() {
        return orderedMessage;
    }

    public void setOrderedMessage(String orderedMessage) {
        this.orderedMessage = orderedMessage;
    }

    public int getTotalCartCount() {
        return totalCartCount;
    }

    public void setTotalCartCount(int totalCartCount) {
        this.totalCartCount = totalCartCount;
    }
}
