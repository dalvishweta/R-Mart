package com.rmart.customer.shops.products.model;

import com.google.gson.annotations.SerializedName;
import com.rmart.customer.shops.home.model.BaseResponse;

import java.util.ArrayList;

public class ProductsResponce  extends BaseResponse {

    @SerializedName("result")
    public Results results;


}

