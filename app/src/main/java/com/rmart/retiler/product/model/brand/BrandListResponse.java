package com.rmart.retiler.product.model.brand;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BrandListResponse {
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

    public ArrayList<Brand> getBrand() {
        return brand;
    }

    @SerializedName("data")
    @Expose
    private ArrayList<Brand> brand;

    public void setCode(String code) {
        Code = code;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrand(ArrayList<Brand> brand) {
        this.brand = brand;
    }

    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
