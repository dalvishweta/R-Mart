package com.rmart.customerservice.mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentRecharge {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("operator")
        @Expose
        private String operator;
        @SerializedName("recharge_on")
        @Expose
        private int rechargeOn;
        @SerializedName("created_on")
        @Expose
        private String createdOn;
        @SerializedName("last_transaction_amount")
        @Expose
        private int lastTransactionAmount;
        @SerializedName("transaction_type")
        @Expose
        private String transactionType;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public int getRechargeOn() {
            return rechargeOn;
        }

        public void setRechargeOn(int rechargeOn) {
            this.rechargeOn = rechargeOn;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public int getLastTransactionAmount() {
            return lastTransactionAmount;
        }

        public void setLastTransactionAmount(int lastTransactionAmount) {
            this.lastTransactionAmount = lastTransactionAmount;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

    }

