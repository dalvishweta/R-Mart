package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

/**
 * Created by Satya Seshu on 15/09/20.
 */
public class AddToCartResponseDetails extends BaseResponse {

    @SerializedName("data")
    @Expose
    private AddToCartDataResponse addToCartDataResponse;

    public AddToCartDataResponse getAddToCartDataResponse() {
        return addToCartDataResponse;
    }

    public void setAddToCartDataResponse(AddToCartDataResponse addToCartDataResponse) {
        this.addToCartDataResponse = addToCartDataResponse;
    }

    public static class AddToCartDataResponse {

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
}
