package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 22/09/20.
 */
public class CustomerOrderPaymentInfoDetails implements Serializable {

    @SerializedName("order_amount")
    @Expose
    private Integer orderAmount;
    @SerializedName("delivery_charges")
    @Expose
    private Integer deliveryCharges;
    @SerializedName("expected_date_of_delivery")
    @Expose
    private String expectedDateOfDelivery;

    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;

    /*@SerializedName("expected_date_of_delivery")
    @Expose
    private Integer expectedDateDelivery;*/

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Integer deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getExpectedDateOfDelivery() {
        return expectedDateOfDelivery;
    }

    public void setExpectedDateOfDelivery(String expectedDateOfDelivery) {
        this.expectedDateOfDelivery = expectedDateOfDelivery;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    /*public Integer getExpectedDateDelivery() {
        return expectedDateDelivery;
    }

    public void setExpectedDateDelivery(Integer expectedDateDelivery) {
        this.expectedDateDelivery = expectedDateDelivery;
    }*/
}
