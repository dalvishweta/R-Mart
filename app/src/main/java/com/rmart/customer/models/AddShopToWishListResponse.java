package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

/**
 * Created by Satya Seshu on 22/09/20.
 */
public class AddShopToWishListResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private AddShopToWishListResponse.AddShopToWishListDataResponse shopToWishListDataResponse;

    public AddShopToWishListDataResponse getShopToWishListDataResponse() {
        return shopToWishListDataResponse;
    }

    public void setShopToWishListDataResponse(AddShopToWishListDataResponse shopToWishListDataResponse) {
        this.shopToWishListDataResponse = shopToWishListDataResponse;
    }

    public static class AddShopToWishListDataResponse {
        @SerializedName("shop_wishlist_id")
        @Expose
        private int shopWishListId;

        public int getShopWishListId() {
            return shopWishListId;
        }

        public void setShopWishListId(int shopWishListId) {
            this.shopWishListId = shopWishListId;
        }
    }
}
