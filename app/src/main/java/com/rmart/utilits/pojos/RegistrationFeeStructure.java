package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.baseclass.views.BaseFragment;

public class RegistrationFeeStructure extends BaseResponse {

    @SerializedName("pay_type")
    @Expose
    String payType;
    @SerializedName("amount")
    @Expose
    String amount;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
