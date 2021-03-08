package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class paybill  implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private RokadResponse data;

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RokadResponse getData() {
        return data;
    }

    public void setData(RokadResponse data) {
        this.data = data;
    }

}
