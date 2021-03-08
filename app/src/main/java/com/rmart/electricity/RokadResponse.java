package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RokadResponse implements Serializable {
    @SerializedName("ResponseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("MerTxnID")
    @Expose
    private String merTxnID;
    @SerializedName("ConsumerID")
    @Expose
    private long consumerID;
    @SerializedName("OrderId")
    @Expose
    private long orderId;
    @SerializedName("DueAmount")
    @Expose
    private int dueAmount;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("BillDate")
    @Expose
    private String billDate;
    @SerializedName("OperatorTxnId")
    @Expose
    private String operatorTxnId;
    @SerializedName("RokadTransNo")
    @Expose
    private long rokadTransNo;

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

    public long getConsumerID() {
        return  consumerID;
    }

    public void setConsumerID(long consumerID) {
        this.consumerID = consumerID;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public String getOperatorTxnId() {
        return operatorTxnId;
    }

    public void setOperatorTxnId(String operatorTxnId) {
        this.operatorTxnId = operatorTxnId;
    }

    public long getRokadTransNo() {
        return rokadTransNo;
    }

    public void setRokadTransNo(long rokadTransNo) {
        this.rokadTransNo = rokadTransNo;
    }

}
