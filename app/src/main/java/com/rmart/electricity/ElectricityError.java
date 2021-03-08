package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElectricityError {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("ConsumerID")
    @Expose
    private String consumerID;
    @SerializedName("bill_unit")
    @Expose
    private String billUnit;
    @SerializedName("mobile_numbe")
    @Expose
    private String mobileNumbe;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(String consumerID) {
        this.consumerID = consumerID;
    }

    public String getBillUnit() {
        return billUnit;
    }

    public void setBillUnit(String billUnit) {
        this.billUnit = billUnit;
    }

    public String getMobileNumbe() {
        return mobileNumbe;
    }

    public void setMobileNumbe(String mobileNumbe) {
        this.mobileNumbe = mobileNumbe;
    }


}
