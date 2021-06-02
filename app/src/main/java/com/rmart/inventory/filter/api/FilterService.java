package com.rmart.inventory.filter.api;

import com.rmart.BuildConfig;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.inventory.filter.models.ProductBrandReponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FilterService {
    @POST(BuildConfig.INVENTORY_FILTER_BRAND)
    @FormUrlEncoded
    Call<ProductBrandReponse> getProductBrandInfo(@Field("start_index") String start_index,@Field("end_index") String end_index);

    @POST(BuildConfig.INVENTORY_FILTER_CATEGORY)
    @FormUrlEncoded
    Call<com.rmart.utilits.pojos.LoginResponse> VerifyOTP(@Field("username") String username,
                                                          @Field("role_id") String role_id,@Field("otp") String otp,@Field("device_id") String deviceKey);

}
