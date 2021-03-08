package com.rmart.customerservice.mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastTransaction {
    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("operator")
    @Expose
    String operator;

    @SerializedName("recharge_on")
    @Expose
    String rechargeOn;

    @SerializedName("last_transaction_amount")
    @Expose
    String lastTransactionAmount;

    @SerializedName("created_by")
    @Expose
    String createdBy;

    @SerializedName("created_on")
    @Expose
    String createdOn;

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }


    @SerializedName("state")
    @Expose
    String stateName;

    int operatorLogo;
    String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getOperatorLogo() {
        return operatorLogo;
    }

    public void setOperatorLogo(int operatorLogo) {
        this.operatorLogo = operatorLogo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRechargeOn() {
        return rechargeOn;
    }

    public void setRechargeOn(String rechargeOn) {
        this.rechargeOn = rechargeOn;
    }

    public String getLastTransactionAmount() {
        return lastTransactionAmount;
    }

    public void setLastTransactionAmount(String lastTransactionAmount) {
        this.lastTransactionAmount = lastTransactionAmount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
