package com.rmart.customer.shops.category.api;

import com.rmart.BuildConfig;
import com.rmart.customer.shops.category.model.CategoryBaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface Category {


    @POST(BuildConfig.PRODUCT_CATEGORY_NEW)
    Call<CategoryBaseResponse> getProductCategory(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("category_ids") String category_ids, @Field("parent_category_id") int parent_category_id);
}