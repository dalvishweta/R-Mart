package com.rmart.utilits.ccavenue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CCAvenueResponse implements Serializable {

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("tracking_id")
    @Expose
    private String trackingId;
    @SerializedName("bank_ref_no")
    @Expose
    private String bankRefNo;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_message")
    @Expose
    private String orderMessage;
    @SerializedName("total_cart_count")
    @Expose
    private Integer totalCartCount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getBankRefNo() {
        return bankRefNo;
    }

    public void setBankRefNo(String bankRefNo) {
        this.bankRefNo = bankRefNo;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public Integer getTotalCartCount() {
        return totalCartCount;
    }

    public void setTotalCartCount(Integer totalCartCount) {
        this.totalCartCount = totalCartCount;
    }
}
