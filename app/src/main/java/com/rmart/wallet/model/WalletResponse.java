package com.rmart.wallet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WalletResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("data")
    @Expose
    public WalletDetails data;
    @SerializedName("msg")
    @Expose
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WalletDetails getData() {
        return data;
    }

    public void setData(WalletDetails data) {
        this.data = data;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}