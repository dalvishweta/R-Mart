package com.rmart.customer.shops.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShopHomePageResponce  extends BaseResponse {

    @SerializedName("result")
    public ArrayList<Results> results;


}

