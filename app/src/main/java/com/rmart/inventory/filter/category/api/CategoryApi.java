package com.rmart.inventory.filter.category.api;

import com.rmart.BuildConfig;
import com.rmart.inventory.filter.category.models.ProductCategoryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CategoryApi {
    @POST(BuildConfig.INVENTORY_FILTER_CATEGORY)
    @FormUrlEncoded
    Call<ProductCategoryResponse> getProductCategoryInfo(@Field("start_index") String start_index, @Field("end_index") String end_index,@Field("category_id") String categoryId, @Field("vendor_id") String vendor_id,@Field("status") String Status);

}
