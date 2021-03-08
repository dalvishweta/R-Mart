package com.rmart.customerservice.mobile.models.mPlans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class PostpaidRecords {

    @SerializedName("desc")
    @Expose
    String desc;

    @SerializedName("rs")
    @Expose
    int rs;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }
}
