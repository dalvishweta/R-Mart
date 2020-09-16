package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 16/09/20.
 */
public class ShoppingCartResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ShoppingCartResponse.ShoppingCartShopWiseResponse shoppingCartResponse;

    public ShoppingCartShopWiseResponse getShoppingCartResponse() {
        return shoppingCartResponse;
    }

    public void setShoppingCartResponse(ShoppingCartShopWiseResponse shoppingCartResponse) {
        this.shoppingCartResponse = shoppingCartResponse;
    }

    public static class ShoppingCartShopWiseResponse {

        @SerializedName("shop_wise_cart_data")
        @Expose
        private List<ShoppingCartResponseDetails> shopWiseCartDataList = null;

        public List<ShoppingCartResponseDetails> getShopWiseCartDataList() {
            return shopWiseCartDataList;
        }

        public void setShopWiseCartDataList(List<ShoppingCartResponseDetails> shopWiseCartDataList) {
            this.shopWiseCartDataList = shopWiseCartDataList;
        }
    }
}
