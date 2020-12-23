package com.rmart.utilits.pojos.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.io.Serializable;

public class OrderProductListResponse extends BaseResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private OrderProductList orderProductLists;

    public OrderProductList getOrderStates() {
        return orderProductLists;
    }

    public void setOrderStates(OrderProductList orderStates) {
        this.orderProductLists = orderStates;
    }
}
