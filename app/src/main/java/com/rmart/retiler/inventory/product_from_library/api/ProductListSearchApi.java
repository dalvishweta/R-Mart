package com.rmart.retiler.inventory.product_from_library.api;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.product_from_library.model.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ProductListSearchApi {

    @POST(BuildConfig.API_PRODUCTSEARCH_LIST)
    @FormUrlEncoded
        Call<ProductListResponse> getProductSearch(@Field("category_ids") String category_ids, @Field("brand_id") String brand_id, @Field("search_phrase") String search_phrase, @Field("page") String page);
}
