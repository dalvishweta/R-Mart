package com.rmart.customerservice.mobile.operators.model;

import com.google.gson.annotations.SerializedName;
import com.rmart.customer.shops.home.model.BaseResponse;
import com.rmart.customer.shops.home.model.Results;

import java.util.ArrayList;

public class OperatorResponse extends BaseResponse {

    @SerializedName("data")
    public  OperatorData operatorData;
}
