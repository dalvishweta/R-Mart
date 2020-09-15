package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 15/09/20.
 */
public class AddToCardResponseModel implements Serializable {

    @SerializedName("total_cart_count")
    @Expose
    private Integer totalCartCount;

    public Integer getTotalCartCount() {
        return totalCartCount;
    }

    public void setTotalCartCount(Integer totalCartCount) {
        this.totalCartCount = totalCartCount;
    }
}
