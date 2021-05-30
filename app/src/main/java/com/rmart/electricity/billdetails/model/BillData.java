package com.rmart.electricity.billdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class BillData {

        @SerializedName("ResponseStatus")
        @Expose
        private String responseStatus;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("MerTxnID")
        @Expose
        private String merTxnID;
        @SerializedName("ConsumerID")
        @Expose
        private String consumerID;
        @SerializedName("OrderId")
        @Expose
        private String orderId;
        @SerializedName("ConsumerName")
        @Expose
        private String consumerName;
        @SerializedName("DueAmount")
        @Expose
        private String dueAmount;
        @SerializedName("DueDate")
        @Expose
        private String dueDate;
        @SerializedName("BillDate")
        @Expose
        private String billDate;
        @SerializedName("OperatorTxnId")
        @Expose
        private String operatorTxnId;

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMerTxnID() {
            return merTxnID;
        }

        public void setMerTxnID(String merTxnID) {
            this.merTxnID = merTxnID;
        }

        public String getConsumerID() {
            return consumerID;
        }

        public void setConsumerID(String consumerID) {
            this.consumerID = consumerID;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getConsumerName() {
            return consumerName;
        }

        public void setConsumerName(String consumerName) {
            this.consumerName = consumerName;
        }

        public String getDueAmount() {
            return dueAmount;
        }

        public void setDueAmount(String dueAmount) {
            this.dueAmount = dueAmount;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getBillDate() {
            return billDate;
        }

        public void setBillDate(String billDate) {
            this.billDate = billDate;
        }

        public String getOperatorTxnId() {
            return operatorTxnId;
        }

        public void setOperatorTxnId(String operatorTxnId) {
            this.operatorTxnId = operatorTxnId;
        }

    }

