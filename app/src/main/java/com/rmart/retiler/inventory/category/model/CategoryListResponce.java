package com.rmart.retiler.inventory.category.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.util.ArrayList;

public class CategoryListResponce extends BaseResponse {

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

    public String getCode() {
        return Code;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private ArrayList<Category> categories;


}

