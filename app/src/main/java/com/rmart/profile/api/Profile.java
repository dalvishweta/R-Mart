package com.rmart.profile.api;

import com.rmart.BuildConfig;
import com.rmart.customer.shops.home.model.ShopHomePageResponce;
import com.rmart.profile.model.ShopTypeResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Profile {


    @GET(BuildConfig.SHOPTYPEDETAILS)
    Call<ShopTypeResponce> getShopTypes(@Query("client_id") String clientId);
}
