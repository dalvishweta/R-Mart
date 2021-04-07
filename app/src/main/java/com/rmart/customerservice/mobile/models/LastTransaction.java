package com.rmart.customerservice.mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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


        try {
            //"2021-03-15 21:50:05"
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date newDate = format.parse(createdOn);
            //8 march 2021
            format = new SimpleDateFormat("dd MMM yyyy");
            String date = format.format(newDate);
            return date;
        } catch (Exception e){
            e.printStackTrace();

        }
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
