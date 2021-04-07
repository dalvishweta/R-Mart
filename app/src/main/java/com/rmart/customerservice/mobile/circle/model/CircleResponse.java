package com.rmart.customerservice.mobile.circle.model;

import com.google.gson.annotations.SerializedName;
import com.rmart.customer.shops.home.model.BaseResponse;
import com.rmart.customerservice.mobile.operators.model.OperatorData;

public class CircleResponse extends BaseResponse {

    @SerializedName("data")
    public CircleData circleData;
}
