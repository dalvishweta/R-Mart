package com.rmart.wallet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckWalletTopup implements Serializable {


        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("code")
        @Expose
        private int code;
        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("request_id")
        @Expose
        private int requestId;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

    }

