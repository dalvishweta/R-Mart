package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class WishListResponseModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private WishListResponseModel.WishListResponseDataResponse wishListResponseDataResponse;

    public WishListResponseDataResponse getWishListResponseDataResponse() {
        return wishListResponseDataResponse;
    }

    public void setWishListResponseDataResponse(WishListResponseDataResponse wishListResponseDataResponse) {
        this.wishListResponseDataResponse = wishListResponseDataResponse;
    }

    public static class WishListResponseDataResponse {
        @SerializedName("shop_wise_wishlist_data")
        @Expose
        private List<WishListResponseDetails> wishListResponseDetails;

        public List<WishListResponseDetails> getWishListResponseDetails() {
            return wishListResponseDetails;
        }

        public void setWishListResponseDetails(List<WishListResponseDetails> wishListResponseDetails) {
            this.wishListResponseDetails = wishListResponseDetails;
        }
    }
}
