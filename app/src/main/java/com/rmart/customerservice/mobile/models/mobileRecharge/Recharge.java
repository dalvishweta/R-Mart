package com.rmart.customerservice.mobile.models.mobileRecharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recharge implements Serializable  {


    @SerializedName("order_id")
    @Expose
    private String orderId;
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
    @SerializedName("failure_message")
    @Expose
    private String failureMessage;
    @SerializedName("card_name")
    @Expose
    private String cardName;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("billing_name")
    @Expose
    private String billingName;
    @SerializedName("billing_address")
    @Expose
    private String billingAddress;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("billing_state")
    @Expose
    private String billingState;
    @SerializedName("billing_zip")
    @Expose
    private String billingZip;
    @SerializedName("billing_country")
    @Expose
    private String billingCountry;
    @SerializedName("billing_tel")
    @Expose
    private String billingTel;
    @SerializedName("billing_email")
    @Expose
    private String billingEmail;
    @SerializedName("delivery_name")
    @Expose
    private String deliveryName;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("delivery_city")
    @Expose
    private String deliveryCity;
    @SerializedName("delivery_state")
    @Expose
    private String deliveryState;
    @SerializedName("delivery_zip")
    @Expose
    private String deliveryZip;
    @SerializedName("delivery_country")
    @Expose
    private String deliveryCountry;
    @SerializedName("delivery_tel")
    @Expose
    private String deliveryTel;
    @SerializedName("mer_amount")
    @Expose
    private String merAmount;
    @SerializedName("response_code")
    @Expose
    private String responseCode;
    @SerializedName("trans_date")
    @Expose
    private String transDate;
    @SerializedName("discount_value")
    @Expose
    private String discountValue;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("service_order_id")
    @Expose
    private String serviceOrderId;
    @SerializedName("service_id")
    @Expose
    private int serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("messageheading")
    @Expose
    private String messageheading;
    @SerializedName("message")
    @Expose
    private String message;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
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

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingTel() {
        return billingTel;
    }

    public void setBillingTel(String billingTel) {
        this.billingTel = billingTel;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public String getDeliveryTel() {
        return deliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }

    public String getMerAmount() {
        return merAmount;
    }

    public void setMerAmount(String merAmount) {
        this.merAmount = merAmount;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessageheading() {
        return messageheading;
    }

    public void setMessageheading(String messageheading) {
        this.messageheading = messageheading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    }