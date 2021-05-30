package com.rmart.electricity.billdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillDataBaseClass {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private BillData data;

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

    public BillData getData() {
        return data;
    }

    public void setData(BillData data) {
        this.data = data;
    }

}
