package com.rmart.retiler.product.model.product.addproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductResponse {
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("code")
    @Expose
    String code;
    @SerializedName("request_id")
    @Expose
    int requestId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "AddProductResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", requestId=" + requestId +
                '}';
    }
}
