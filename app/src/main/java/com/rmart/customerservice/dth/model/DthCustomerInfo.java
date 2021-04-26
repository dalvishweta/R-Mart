package com.rmart.customerservice.dth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DthCustomerInfo implements Serializable {

        @SerializedName("tel")
        @Expose
        private int tel;
        @SerializedName("operator")
        @Expose
        private String operator;
        @SerializedName("records")
        @Expose
        private Object records;
        @SerializedName("status")
        @Expose
        private int status;

        public int getTel() {
            return tel;
        }

        public void setTel(int tel) {
            this.tel = tel;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public Object getRecords() {
            return records;
        }

        public void setRecords(Object records) {
            this.records = records;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
