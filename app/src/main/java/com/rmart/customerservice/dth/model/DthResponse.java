package com.rmart.customerservice.dth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DthResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("data")
    @Expose
    public DthCustomerInfo data;
    @SerializedName("msg")
    @Expose
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DthCustomerInfo getData() {
        return data;
    }

    public void setData(DthCustomerInfo data) {
        this.data = data;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}