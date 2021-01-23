package com.rmart.retiler.inventory.product_from_library.model;

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
    private int total_count;

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("next_value")
    @Expose
    private boolean next_value;

    @SerializedName("data")
    @Expose
    private ArrayList<Product> product;


    public void setCode(String code) {
        Code = code;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNext_value() {
        return next_value;
    }

    public void setNext_value(boolean next_value) {
        this.next_value = next_value;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }


    public String getCode() {
        return Code;
    }

    public int getTotal_count() {
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

}

