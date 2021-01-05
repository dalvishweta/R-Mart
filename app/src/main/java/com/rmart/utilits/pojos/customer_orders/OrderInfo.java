package com.rmart.utilits.pojos.customer_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderInfo implements Serializable {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("order_id")
    @Expose
    String orderID;

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public double getCoupon_value() {
        return coupon_value;
    }

    public String getCouponCode() {
        return couponCode;
    }

    @SerializedName("receipt_number")
    @Expose
    String receiptNumber;

    @SerializedName("mode_of_payment")
    @Expose
    String modeOfPayment;

    @SerializedName("delivery_charges")
    @Expose
    double deliveryCharges;

    @SerializedName("order_amount")
    @Expose
    double orderAmount;

    @SerializedName("status_name")
    @Expose
    String statusName;

    @SerializedName("created_date")
    @Expose
    String createdDate;

    @SerializedName("delivery_method")
    @Expose
    public String deliveryMethod;

    @SerializedName("delivery_status")
    @Expose
    String deliveryStatus;

    @SerializedName("order_charges")
    @Expose
    double orderCharges;

    @SerializedName("coupon_value")
    @Expose
    double coupon_value;
    @SerializedName("coupon_code")
    @Expose
    String couponCode;

    @SerializedName("total_amount")
    @Expose
    double totalAmt;
    @SerializedName("status_comment")
    @Expose
    String statusComments;
    @SerializedName("order_date")
    @Expose
    private String orderDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public double getOrderCharges() {
        return orderCharges;
    }

    public void setOrderCharges(double orderCharges) {
        this.orderCharges = orderCharges;
    }

    public String getStatusComments() {
        return statusComments;
    }

    public void setStatusComments(String statusComments) {
        this.statusComments = statusComments;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
