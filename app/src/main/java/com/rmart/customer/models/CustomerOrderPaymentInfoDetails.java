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
    private double orderAmount;
    @SerializedName("delivery_charges")
    @Expose
    private Integer deliveryCharges;
    @SerializedName("expected_date_of_delivery")
    @Expose
    private String expectedDateOfDelivery;

    @SerializedName("total_amount")
    @Expose
    private double totalAmount;
    @SerializedName("payment_total_amount")
    @Expose
    private double payment_total_amount;
    @SerializedName("wallet_amount")
    @Expose
    private double wallet_amount;

    @SerializedName("coupon_discount_amount")
    @Expose
    private double siscountAmount;
    @SerializedName("coupon_code_status")
    @Expose
    private boolean couponCodeStatus;
    @SerializedName("coupon_code_message")
    @Expose
    private String couponMessage;

    public double getSiscountAmount() {
        return siscountAmount;
    }

    public boolean isCouponCodeStatus() {
        return couponCodeStatus;
    }

    public String getCouponMessage() {
        return couponMessage;
    }
/*@SerializedName("expected_date_of_delivery")
    @Expose
    private Integer expectedDateDelivery;*/

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /*public Integer getExpectedDateDelivery() {
        return expectedDateDelivery;
    }

    public void setExpectedDateDelivery(Integer expectedDateDelivery) {
        this.expectedDateDelivery = expectedDateDelivery;
    }*/

    public double getPayment_total_amount() {
        return payment_total_amount;
    }

    public void setPayment_total_amount(double payment_total_amount) {
        this.payment_total_amount = payment_total_amount;
    }

    public double getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(double wallet_amount) {
        this.wallet_amount = wallet_amount;
    }
}
