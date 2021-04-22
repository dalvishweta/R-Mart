package com.rmart.customerservice.mobile.models.mPlans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostPaidResponseGetPlans {

    @SerializedName("status")
    @Expose
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PostPaidData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PostPaidData getData() {
        return data;
    }

    public void setData(PostPaidData data) {
        this.data = data;
    }

}
