package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.utilits.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 30/10/20.
 */
public class ShopFavouritesListResponseModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ShopFavouritesListResponseModel.ShopFavouritesListDataResponse shopFavouritesListDataResponse;

    public ShopFavouritesListDataResponse getShopFavouritesListDataResponse() {
        return shopFavouritesListDataResponse;
    }

    public void setShopFavouritesListDataResponse(ShopFavouritesListDataResponse shopFavouritesListDataResponse) {
        this.shopFavouritesListDataResponse = shopFavouritesListDataResponse;
    }

    public static class ShopFavouritesListDataResponse {

        @SerializedName("customer_fav_shop_data")
        @Expose
        private List<CustomerProductsShopDetailsModel>  customerProductsShopDetails;

        public List<CustomerProductsShopDetailsModel> getCustomerProductsShopDetails() {
            return customerProductsShopDetails;
        }

        public void setCustomerProductsShopDetails(List<CustomerProductsShopDetailsModel> customerProductsShopDetails) {
            this.customerProductsShopDetails = customerProductsShopDetails;
        }
    }
}