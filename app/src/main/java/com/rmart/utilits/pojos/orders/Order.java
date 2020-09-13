package com.rmart.utilits.pojos.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

public class Order extends BaseResponse {
    @SerializedName("order_id")
    @Expose
    private String orderID;

    @SerializedName("order_date")
    @Expose
    private String orderDate;

    @SerializedName("product_count")
    @Expose
    private String productCount;

    private String orderStatus;
    private String orderStatusID;
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusID() {
        return orderStatusID;
    }

    public void setOrderStatusID(String orderStatusID) {
        this.orderStatusID = orderStatusID;
    }
}
