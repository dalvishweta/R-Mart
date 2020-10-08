package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 27/09/20.
 */
public class RSAKeyResponseDetails implements Serializable {

    private String userMobileNumber;
    @SerializedName("billing_name")
    @Expose
    private String billingName;
    @SerializedName("billing_email")
    @Expose
    private String billingEmail;
    @SerializedName("billing_tel")
    @Expose
    private String billingTel;
    @SerializedName("billing_address")
    @Expose
    private String billingAddress;
    @SerializedName("billing_zip")
    @Expose
    private String billingZip;
    @SerializedName("billing_state")
    @Expose
    private String billingState;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("billing_country")
    @Expose
    private String billingCountry;
    @SerializedName("redirect_url")
    @Expose
    private String redirectUrl;
    @SerializedName("cancel_url")
    @Expose
    private String cancelUrl;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("merchant_param1")
    @Expose
    private Integer merchantParam1;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("rsa_key")
    @Expose
    private String rsaKey;
    @SerializedName("access_code")
    @Expose
    private String accessCode;
    private String otpMsg;

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getBillingTel() {
        return billingTel;
    }

    public void setBillingTel(String billingTel) {
        this.billingTel = billingTel;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getMerchantParam1() {
        return merchantParam1;
    }

    public void setMerchantParam1(Integer merchantParam1) {
        this.merchantParam1 = merchantParam1;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRsaKey() {
        return rsaKey;
    }

    public void setRsaKey(String rsaKey) {
        this.rsaKey = rsaKey;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public void setOTPMsg(String msg) {
        otpMsg = msg;
    }
    public String getOTPMsg() {
        return otpMsg;
    }
}
