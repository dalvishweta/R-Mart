package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class ShopWiseWishListResponseModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ShopWiseWishListResponseModel.ShopWiseWishListDataResponse shopWiseWishListDataResponse;

    public ShopWiseWishListDataResponse getShopWiseWishListDataResponse() {
        return shopWiseWishListDataResponse;
    }

    public void setShopWiseWishListDataResponse(ShopWiseWishListDataResponse shopWiseWishListDataResponse) {
        this.shopWiseWishListDataResponse = shopWiseWishListDataResponse;
    }

    public static class ShopWiseWishListDataResponse {
        @SerializedName("shop_wise_wishlist_data")
        @Expose
        private List<ShopWiseWishListResponseDetails> shopWiseWishListResponseDetailsList;

        public List<ShopWiseWishListResponseDetails> getShopWiseWishListResponseDetailsList() {
            return shopWiseWishListResponseDetailsList;
        }

        public void setShopWiseWishListResponseDetailsList(List<ShopWiseWishListResponseDetails> shopWiseWishListResponseDetailsList) {
            this.shopWiseWishListResponseDetailsList = shopWiseWishListResponseDetailsList;
        }
    }
}
