package com.rmart.retiler.product.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductListResponse {
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("Code")
    @Expose
    String code;
    @SerializedName("start_index")
    @Expose
    String start_index;
    @SerializedName("end_index")
    @Expose
    String endIndex;
    @SerializedName("product_image_path")
    @Expose
    String productImagPath;
    @SerializedName("product_count")
    @Expose
    int productCount;
    @SerializedName("data")
    @Expose
    ArrayList<Product> products ;
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

    public String getStart_index() {
        return start_index;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public String getProductImagPath() {
        return productImagPath;
    }

    public int getProductCount() {
        return productCount;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "ProductListResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", start_index='" + start_index + '\'' +
                ", endIndex='" + endIndex + '\'' +
                ", productImagPath='" + productImagPath + '\'' +
                ", productCount=" + productCount +
                ", products=" + products +
                ", requestId=" + requestId +
                '}';
    }
}
