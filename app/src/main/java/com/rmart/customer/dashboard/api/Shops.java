package com.rmart.customer.dashboard.api;

import com.rmart.BuildConfig;
import com.rmart.customer.dashboard.model.ShopHomePageResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Shops {

    @GET(BuildConfig.CUSTOMER_SHOPS_HOME_PAGE_NEW)
    Call<ShopHomePageResponce> getShopHomePageNEW(@Query("client_id") String clientId);
}
