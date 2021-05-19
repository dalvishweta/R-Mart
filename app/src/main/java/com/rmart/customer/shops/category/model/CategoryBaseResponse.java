package com.rmart.customer.shops.category.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryBaseResponse implements Serializable {

        @SerializedName("status")
        @Expose
        private int status;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("result")
        @Expose
        private Result result;
        @SerializedName("request_id")
        @Expose
        private int requestId;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

    }

