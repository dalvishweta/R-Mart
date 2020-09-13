package com.rmart.utilits.pojos.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderStateListResponse extends BaseResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private ArrayList<StateOfOrders> orderStates;

    public ArrayList<StateOfOrders> getOrderStates() {
        return orderStates;
    }

    public void setOrderStates(ArrayList<StateOfOrders> orderStates) {
        this.orderStates = orderStates;
    }
}
