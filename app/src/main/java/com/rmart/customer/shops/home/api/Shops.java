package com.rmart.customer.shops.home.api;

import com.rmart.BuildConfig;
import com.rmart.customer.shops.home.model.ShopHomePageResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Shops {


    @GET(BuildConfig.CUSTOMER_SHOPS_HOME_PAGE)
    Call<ShopHomePageResponce> getShopHomePage(@Query("client_id") String clientId, @Query("vendor_id") int vendorId, @Query("shop_id") int shop_id,
                                               @Query("customer_id") String customerId,@Query("role_id") String roleID);
}
