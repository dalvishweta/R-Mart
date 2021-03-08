package com.rmart.customerservice.mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RokadPaymentRequest implements Serializable {

        @SerializedName("servicetype")
        @Expose
        private int servicetype;
        @SerializedName("pre_operator_dth")
        @Expose
        private String preOperatorDth;
        @SerializedName("customer_number")
        @Expose
        private String customerNumber;
        @SerializedName("rechargetype")
        @Expose
        private String rechargetype;
        @SerializedName("pre_operator")
        @Expose
        private String preOperator;
        @SerializedName("post_operator")
        @Expose
        private String postOperator;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("mobile_number")
        @Expose
        private String mobileNumber;
        @SerializedName("recharge_amount")
        @Expose
        private String rechargeAmount;
        @SerializedName("recharge_type")
        @Expose
        private String rechargeType;
        @SerializedName("user_id")
        @Expose
        private int userId;

        public int getServicetype() {
            return servicetype;
        }

        public void setServicetype(int servicetype) {
            this.servicetype = servicetype;
        }

        public String getPreOperatorDth() {
            return preOperatorDth;
        }

        public void setPreOperatorDth(String preOperatorDth) {
            this.preOperatorDth = preOperatorDth;
        }

        public String getCustomerNumber() {
            return customerNumber;
        }

        public void setCustomerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
        }

        public String getRechargetype() {
            return rechargetype;
        }

        public void setRechargetype(String rechargetype) {
            this.rechargetype = rechargetype;
        }

        public String getPreOperator() {
            return preOperator;
        }

        public void setPreOperator(String preOperator) {
            this.preOperator = preOperator;
        }

        public String getPostOperator() {
            return postOperator;
        }

        public void setPostOperator(String postOperator) {
            this.postOperator = postOperator;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(String rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public String getRechargeType() {
            return rechargeType;
        }

        public void setRechargeType(String rechargeType) {
            this.rechargeType = rechargeType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

    }

