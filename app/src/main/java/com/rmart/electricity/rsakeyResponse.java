package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

public class rsakeyResponse extends BaseResponse {

    @SerializedName("request_id")
    @Expose
    private String request_id = "";
    @SerializedName("data")
    @Expose
    private CCavenueres data;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }


    public CCavenueres getData() {
        return data;
    }

    public void setData(CCavenueres data) {
        this.data = data;
    }








}