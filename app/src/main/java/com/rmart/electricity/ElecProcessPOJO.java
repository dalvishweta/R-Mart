package com.rmart.electricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ElecProcessPOJO implements Serializable {

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("data")
        @Expose
        private data data;
        @SerializedName("msg")
        @Expose
        private String msg;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public data getData() {
            return data;
        }

        public void setData(data data) {
            this.data = data;
        }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
