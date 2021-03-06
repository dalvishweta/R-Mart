package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

public class RSAKeyResponse extends BaseResponse {

    @SerializedName("request_id")
    @Expose
    private int request_id ;
    @SerializedName("data")
    @Expose
    private CCavenueres data;

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }


    public CCavenueres getData() {
        return data;
    }

    public void setData(CCavenueres data) {
        this.data = data;
    }








}