package com.rmart.electricity.fetchbill.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BillDetails implements Serializable {

    @SerializedName("ResponseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("MerTxnID")
    @Expose
    private String merTxnID = null;
    @SerializedName("ConsumerID")
    @Expose
    private String consumerID;
    @SerializedName("OrderId")
    @Expose
    private String orderId;
    @SerializedName("ConsumerName")
    @Expose
    private String consumerName;
    @SerializedName("DueAmount")
    @Expose
    private double dueAmount;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("BillDate")
    @Expose
    private String billDate;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setDueAmount(Integer dueAmount) {
        this.dueAmount = dueAmount;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMobileNumbe() {
        return mobileNumbe;
    }

    public void setMobileNumbe(String mobileNumbe) {
        this.mobileNumbe = mobileNumbe;
    }

    @SerializedName("mobile_number")
    @Expose
    private String mobileNumbe;
    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerTxnID() {
        return merTxnID;
    }

    public void setMerTxnID(String merTxnID) {
        this.merTxnID = merTxnID;
    }

    public String getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(String consumerID) {
        this.consumerID = consumerID;
    }

    public String getOrderId() {
        return orderId;
    }



    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(int dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getServiceId() {
        return serviceId;
    }



    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}

