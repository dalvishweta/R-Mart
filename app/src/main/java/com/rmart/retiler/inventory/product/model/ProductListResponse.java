package com.rmart.retiler.inventory.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.util.ArrayList;

public class ProductListResponse extends BaseResponse {

    @SerializedName("Code")
    @Expose
    private String Code;

    @SerializedName("total_count")
    @Expose
    private String total_count;

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("type")
    @Expose
    private String type;

    public String getCode() {
        return Code;
    }

    public String getTotal_count() {
        return total_count;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Product> getProduct() {

        return product;
    }

    @SerializedName("data")
    @Expose
    private ArrayList<Product> product;


}

