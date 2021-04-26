package com.rmart.customerservice.dth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Records {
    @SerializedName("MonthlyRecharge")
    @Expose
    private int monthlyRecharge;
    @SerializedName("Balance")
    @Expose
    private float balance;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("NextRechargeDate")
    @Expose
    private String nextRechargeDate;
    @SerializedName("planname")
    @Expose
    private String planname;

    public int getMonthlyRecharge() {
        return monthlyRecharge;
    }

    public void setMonthlyRecharge(int monthlyRecharge) {
        this.monthlyRecharge = monthlyRecharge;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextRechargeDate() {
        return nextRechargeDate;
    }

    public void setNextRechargeDate(String nextRechargeDate) {
        this.nextRechargeDate = nextRechargeDate;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

}
