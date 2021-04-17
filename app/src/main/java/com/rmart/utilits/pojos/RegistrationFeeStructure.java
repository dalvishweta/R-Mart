package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.baseclass.views.BaseFragment;

public class RegistrationFeeStructure {

    @SerializedName("pay_type")
    @Expose
    String payType;

    @SerializedName("amount")
    @Expose
    String amount;

    @SerializedName("position")
    @Expose
    String position;

    public String getPayType() {
        return payType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
