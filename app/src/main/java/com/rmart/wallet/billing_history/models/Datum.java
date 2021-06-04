
package com.rmart.wallet.billing_history.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("transaction_no")
    @Expose
    private Object transactionNo;
    @SerializedName("wallet_id")
    @Expose
    private Integer walletId;
    @SerializedName("amt")
    @Expose
    private String amt;
    @SerializedName("amt_before_trans")
    @Expose
    private String amtBeforeTrans;
    @SerializedName("amt_after_trans")
    @Expose
    private String amtAfterTrans;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("cr_db_type")
    @Expose
    private String crDbType;
    @SerializedName("trans_type")
    @Expose
    private Integer transType;
    @SerializedName("wallet_status")
    @Expose
    private String walletStatus;
    @SerializedName("is_rokad_comm_credit")
    @Expose
    private Integer isRokadCommCredit;
    @SerializedName("order_id")
    @Expose
    private Object orderId;
    @SerializedName("rokad_trans_details_id")
    @Expose
    private Object rokadTransDetailsId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;

    public Object getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Object transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getAmtBeforeTrans() {
        return amtBeforeTrans;
    }

    public void setAmtBeforeTrans(String amtBeforeTrans) {
        this.amtBeforeTrans = amtBeforeTrans;
    }

    public String getAmtAfterTrans() {
        return amtAfterTrans;
    }

    public void setAmtAfterTrans(String amtAfterTrans) {
        this.amtAfterTrans = amtAfterTrans;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCrDbType() {
        return crDbType;
    }

    public void setCrDbType(String crDbType) {
        this.crDbType = crDbType;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getWalletStatus() {
        return walletStatus;
    }

    public void setWalletStatus(String walletStatus) {
        this.walletStatus = walletStatus;
    }

    public Integer getIsRokadCommCredit() {
        return isRokadCommCredit;
    }

    public void setIsRokadCommCredit(Integer isRokadCommCredit) {
        this.isRokadCommCredit = isRokadCommCredit;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Object getRokadTransDetailsId() {
        return rokadTransDetailsId;
    }

    public void setRokadTransDetailsId(Object rokadTransDetailsId) {
        this.rokadTransDetailsId = rokadTransDetailsId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
