package com.rmart.retiler.product.model.subCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubCategoryListResponse {
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("Code")
    @Expose
    String code;
    @SerializedName("start_index")
    @Expose
    String startIndex;
    @SerializedName("end_index")
    @Expose
    String endIndex;
    @SerializedName("count")
    @Expose
    int count;
    @SerializedName("type")
    @Expose
    String type;
    @SerializedName("data")
    @Expose
    ArrayList<SubCategory> subCategories;
    @SerializedName("request_id")
    @Expose
    int requestId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
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

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "SubCategoryListResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", startIndex='" + startIndex + '\'' +
                ", endIndex='" + endIndex + '\'' +
                ", count=" + count +
                ", type='" + type + '\'' +
                ", subCategories=" + subCategories +
                ", requestId=" + requestId +
                '}';
    }
}