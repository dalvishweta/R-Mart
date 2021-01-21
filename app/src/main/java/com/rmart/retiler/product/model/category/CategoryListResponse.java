package com.rmart.retiler.product.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryListResponse {
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
    String start_index;
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
    ArrayList<Category> categories ;
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

    public String getStart_index() {
        return start_index;
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

    public int getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "CategoryListResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", start_index='" + start_index + '\'' +
                ", endIndex='" + endIndex + '\'' +
                ", count=" + count +
                ", type='" + type + '\'' +
                ", categories=" + categories +
                ", requestId=" + requestId +
                '}';
    }
}
