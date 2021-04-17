
package com.rmart.customerservice.mobile.models.mPlans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGetPostpaidPlans {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("time")
    @Expose
    private double time;

    @SerializedName("data")
    @Expose
    private PostPaidData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public PostPaidData getData() {
        return data;
    }

    public void setData(PostPaidData data) {
        this.data = data;
    }

}
