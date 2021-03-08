package com.rmart.customerservice.mobile.models.mPlans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostpaidData {
    @SerializedName("tel")
    @Expose
    String tel;

    @SerializedName("operator")
    @Expose
    String operator;

    @SerializedName("records")
    @Expose
    List<RechargePlans> records;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<RechargePlans> getRecords() {
        return records;
    }

    public void setRecords(List<RechargePlans> records) {
        this.records = records;
    }
}
