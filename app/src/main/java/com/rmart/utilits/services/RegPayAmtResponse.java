package com.rmart.utilits.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.pojos.RegistrationFeeStructure;

import java.util.ArrayList;

public class RegPayAmtResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    ArrayList<RegistrationFeeStructure> paymentObjects;

    public ArrayList<RegistrationFeeStructure> getPaymentObjects() {
        return paymentObjects;
    }

    public void setPaymentObjects(ArrayList<RegistrationFeeStructure> paymentObjects) {
        this.paymentObjects = paymentObjects;
    }
}