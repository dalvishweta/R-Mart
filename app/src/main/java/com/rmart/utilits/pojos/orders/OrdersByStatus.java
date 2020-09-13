package com.rmart.utilits.pojos.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class OrdersByStatus extends BaseResponse implements Serializable {

    @SerializedName("start_index")
    @Expose
    private String startIndex;

    @SerializedName("end_index")
    @Expose
    private String endIndex;

    @SerializedName("orders_count")
    @Expose
    private String ordersCount;

    @SerializedName("data")
    @Expose
    private ArrayList<Order> orders;

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public String getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(String ordersCount) {
        this.ordersCount = ordersCount;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
