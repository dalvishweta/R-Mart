package com.rmart.customerservice.mobile.models.mPlans;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PostPaidData {

    @SerializedName("records")
    @Expose
    private List<RechargePlans> records;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("time")
    @Expose
    private Object time;

    public List<RechargePlans> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<RechargePlans> records) {
        this.records = records;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

}
