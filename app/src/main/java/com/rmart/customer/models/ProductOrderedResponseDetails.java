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

    public String getOrderedMessage() {
        return orderedMessage;
    }

    public void setOrderedMessage(String orderedMessage) {
        this.orderedMessage = orderedMessage;
    }
}
