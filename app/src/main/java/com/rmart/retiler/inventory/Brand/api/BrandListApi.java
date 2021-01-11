package com.rmart.retiler.inventory.Brand.api;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.Brand.model.BrandListResponse;
import com.rmart.retiler.inventory.category.model.CategoryListResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface BrandListApi {

    @POST(BuildConfig.API_BRAND_LIST)
    @FormUrlEncoded


    Call<BrandListResponse> getBrandList(@Field("start_index") String startIndex, @Field("end_index") String endIndex, @Field("brand_id") String brandId);
}
