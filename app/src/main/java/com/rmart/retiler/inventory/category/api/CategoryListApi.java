package com.rmart.retiler.inventory.category.api;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.category.model.CategoryListResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface CategoryListApi {

    @POST(BuildConfig.API_CATEGORY_LIST)
    @FormUrlEncoded
    Call<CategoryListResponce> getCategoryList(@Field("start_index") String startIndex, @Field("end_index") String endIndex, @Field("category_id") String categoryId, @Field("vendor_id") String vendor_id);
}
