package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class data implements Serializable {

    @SerializedName("ResponseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("MerTxnID")
    @Expose
    private List<Object> merTxnID = null;
    @SerializedName("ConsumerID")
    @Expose
    private long consumerID;
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("ConsumerName")
    @Expose
    private String consumerName;
    @SerializedName("DueAmount")
    @Expose
    private Integer dueAmount;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("BillDate")
    @Expose
    private String billDate;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setDueAmount(Integer dueAmount) {
        this.dueAmount = dueAmount;
    }

    public void setServiceId(Integer serviceId) {
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

    public List<Object> getMerTxnID() {
        return merTxnID;
    }

    public void setMerTxnID(List<Object> merTxnID) {
        this.merTxnID = merTxnID;
    }

    public long getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(long consumerID) {
        this.consumerID = consumerID;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public int getDueAmount() {
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

}

