package com.rmart.customerservice.mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

public class ResponseGetHistory extends BaseResponse {
    @SerializedName("data")
    @Expose
    public LastTransaction[] lastTransaction;

    public LastTransaction[] getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(LastTransaction[] lastTransaction) {
        this.lastTransaction = lastTransaction;
    }
}
