package com.rmart.customerservice.mobile.models.mobileRecharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobileRechargeBaseClass implements Serializable {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MobileRecharge data;
    @SerializedName("request_id")
    @Expose
    private int requestId;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MobileRecharge getData() {
        return data;
    }

    public void setData(MobileRecharge data) {
        this.data = data;
    }
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

}

