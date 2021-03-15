package com.rmart.customer.dashboard.api;

import com.rmart.BuildConfig;
import com.rmart.customer.dashboard.model.HomePageData;
import com.rmart.customer.dashboard.model.HomePageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Shops {

    @GET(BuildConfig.CUSTOMER_SHOPS_HOME_PAGE_NEW)
    Call<HomePageResponse> getShopHomePageNEW(@Query("client_id") String clientId);
}
