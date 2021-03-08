package com.rmart.customerservice.mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vrecharge implements Serializable  {


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
        private int rechargetype;
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
        private long mobileNumber;
        @SerializedName("recharge_amount")
        @Expose
        private long rechargeAmount;
        @SerializedName("recharge_type")
        @Expose
        private long rechargeType;
        @SerializedName("recharge_from")
        @Expose
        private String rechargeFrom;
        @SerializedName("service")
        @Expose
        private String service;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("plan_type")
        @Expose
        private String planType;
        @SerializedName("service_type")
        @Expose
        private String serviceType;

        @SerializedName("rec_type")
        @Expose
        private String recType;
        @SerializedName("mobile_operator")
        @Expose
        private String mobileOperator;
        @SerializedName("Merchantrefno")
        @Expose
        private long merchantrefno;
        @SerializedName("rokad_user_id")
        @Expose
        private long rokadUserId;
        @SerializedName("operator")
        @Expose
        private String operator;
        @SerializedName("recharge_on")
        @Expose
        private long rechargeOn;
        @SerializedName("last_transaction_amount")
        @Expose
        private long lastTransactionAmount;
        @SerializedName("transaction_id")
        @Expose
        private long transactionId;
        @SerializedName("created_by")
        @Expose
        private long createdBy;
        @SerializedName("transaction_type")
        @Expose
        private String transactionType;
        @SerializedName("venus_api_flag")
        @Expose
        private long venusApiFlag;
        @SerializedName("venus_status")
        @Expose
        private String venusStatus;
        @SerializedName("venus_description")
        @Expose
        private String venusDescription;
        @SerializedName("venus_MerTxnID")
        @Expose
        private long venusMerTxnID;
        @SerializedName("venus_OrderNo")
        @Expose
        private long venusOrderNo;
        @SerializedName("venus_OperatorTxnID")
        @Expose
        private long venusOperatorTxnID;
        @SerializedName("venus_serv_type")
        @Expose
        private int venusServType;

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

        public int getRechargetype() {
            return rechargetype;
        }

        public void setRechargetype(int rechargetype) {
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

        public long getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(int mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public long getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(int rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public long getRechargeType() {
            return rechargeType;
        }

        public void setRechargeType(int rechargeType) {
            this.rechargeType = rechargeType;
        }

        public String getRechargeFrom() {
            return rechargeFrom;
        }

        public void setRechargeFrom(String rechargeFrom) {
            this.rechargeFrom = rechargeFrom;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPlanType() {
            return planType;
        }

        public void setPlanType(String planType) {
            this.planType = planType;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getRecType() {
            return recType;
        }

        public void setRecType(String recType) {
            this.recType = recType;
        }

        public String getMobileOperator() {
            return mobileOperator;
        }

        public void setMobileOperator(String mobileOperator) {
            this.mobileOperator = mobileOperator;
        }

        public long getMerchantrefno() {
            return merchantrefno;
        }

        public void setMerchantrefno(int merchantrefno) {
            this.merchantrefno = merchantrefno;
        }

        public long getRokadUserId() {
            return rokadUserId;
        }

        public void setRokadUserId(int rokadUserId) {
            this.rokadUserId = rokadUserId;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public long getRechargeOn() {
            return rechargeOn;
        }

        public void setRechargeOn(int rechargeOn) {
            this.rechargeOn = rechargeOn;
        }

        public long getLastTransactionAmount() {
            return lastTransactionAmount;
        }

        public void setLastTransactionAmount(int lastTransactionAmount) {
            this.lastTransactionAmount = lastTransactionAmount;
        }

        public long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(int transactionId) {
            this.transactionId = transactionId;
        }

        public long getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(int createdBy) {
            this.createdBy = createdBy;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public long getVenusApiFlag() {
            return venusApiFlag;
        }

        public void setVenusApiFlag(int venusApiFlag) {
            this.venusApiFlag = venusApiFlag;
        }

        public String getVenusStatus() {
            return venusStatus;
        }

        public void setVenusStatus(String venusStatus) {
            this.venusStatus = venusStatus;
        }

        public String getVenusDescription() {
            return venusDescription;
        }

        public void setVenusDescription(String venusDescription) {
            this.venusDescription = venusDescription;
        }

        public long getVenusMerTxnID() {
            return venusMerTxnID;
        }

        public void setVenusMerTxnID(int venusMerTxnID) {
            this.venusMerTxnID = venusMerTxnID;
        }

        public long getVenusOrderNo() {
            return venusOrderNo;
        }

        public void setVenusOrderNo(int venusOrderNo) {
            this.venusOrderNo = venusOrderNo;
        }

        public long getVenusOperatorTxnID() {
            return venusOperatorTxnID;
        }

        public void setVenusOperatorTxnID(int venusOperatorTxnID) {
            this.venusOperatorTxnID = venusOperatorTxnID;
        }

        public int getVenusServType() {
            return venusServType;
        }

        public void setVenusServType(int venusServType) {
            this.venusServType = venusServType;
        }

    }