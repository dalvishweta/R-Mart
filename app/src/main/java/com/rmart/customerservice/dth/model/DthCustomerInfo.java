package com.rmart.customerservice.dth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DthCustomerInfo implements Serializable {

    @SerializedName("tel")
    @Expose
    private long tel;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("records")
    @Expose
    private List<Records> records = null;
    @SerializedName("status")
    @Expose
    private int status;

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<Records> getRecords() {
        return records;
    }

    public void setRecords(List<Records> records) {
        this.records = records;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    }
