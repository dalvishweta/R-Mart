package com.rmart.profile.model;

import com.google.gson.annotations.SerializedName;
import com.rmart.customer.shops.home.model.BaseResponse;

import java.util.ArrayList;


public class ShopTypeResponce  extends BaseResponse {

    @SerializedName("result")
    public ShopTypeResult results;


}

