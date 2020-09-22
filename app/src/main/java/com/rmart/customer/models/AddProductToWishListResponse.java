package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

/**
 * Created by Satya Seshu on 22/09/20.
 */
public class AddProductToWishListResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private AddProductToWishListResponse.AddProductToWishListDataResponse addProductToWishListDataResponse;

    public AddProductToWishListDataResponse getAddProductToWishListDataResponse() {
        return addProductToWishListDataResponse;
    }

    public void setAddProductToWishListDataResponse(AddProductToWishListDataResponse addProductToWishListDataResponse) {
        this.addProductToWishListDataResponse = addProductToWishListDataResponse;
    }

    public static class AddProductToWishListDataResponse {
        @SerializedName("product_wishlist_id")
        @Expose
        private String productWishListId;

        public String getProductWishListId() {
            return productWishListId;
        }

        public void setProductWishListId(String productWishListId) {
            this.productWishListId = productWishListId;
        }
    }
}
