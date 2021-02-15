package com.rmart.customer.shops.list.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.customer.shops.home.model.Results;
import com.rmart.utilits.BaseResponse;

import java.util.ArrayList;

public class ProductSearchResponce extends BaseResponse {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("total_count")
    @Expose
    private int totalCount;
    @SerializedName("next_value")
    @Expose
    private boolean nextValue;

    @SerializedName("data")
    public ArrayList<SearchProducts> data;


}

