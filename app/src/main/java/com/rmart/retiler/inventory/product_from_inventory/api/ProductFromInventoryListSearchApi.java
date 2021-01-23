package com.rmart.retiler.inventory.product_from_inventory.api;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.product_from_inventory.Model.productFromInventoryListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ProductFromInventoryListSearchApi {

    @POST(BuildConfig.API_PRODUCTSEARCH_FROM_INVENTORY_LIST)
    @FormUrlEncoded
    Call<productFromInventoryListResponse> getProductSearch(@Field("category_ids") String category_ids, @Field("mobile") String mobile, @Field("brand_id") String brand_id, @Field("search_phrase") String search_phrase, @Field("page") String page);
}
