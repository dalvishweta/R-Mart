
package com.rmart.customerservice.mobile.mplan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("records")
    @Expose
    private Records records;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("time")
    @Expose
    private Object time;

    public Records getRecords() {
        return records;
    }

    public void setRecords(Records records) {
        this.records = records;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

}
