package com.rmart.customer.shops_new.home.api;

import com.rmart.BuildConfig;
import com.rmart.customer.shops_new.home.model.ShopHomePageResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Shops {

    @GET(BuildConfig.CUSTOMER_SHOPS_HOME_PAGE_NEW)
    Call<ShopHomePageResponce> getShopHomePageNEW(@Query("client_id") String clientId);
}
