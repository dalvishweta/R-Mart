package com.rmart.retiler.inventory.Brand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.util.ArrayList;

public class BrandListResponse extends BaseResponse {

    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("start_index")
    @Expose
    private String startIndex;
    @SerializedName("end_index")
    @Expose
    private String endIndex;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private ArrayList<Brand> brand;


}

