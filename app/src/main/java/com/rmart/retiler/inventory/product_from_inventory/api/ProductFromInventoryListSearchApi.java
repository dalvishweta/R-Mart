package com.rmart.retiler.inventory.product_from_inventory.api;

import com.rmart.BuildConfig;
import com.rmart.inventory.models.UpdateReasponce;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductFromInventoryListResponse;
import com.rmart.utilits.pojos.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface ProductFromInventoryListSearchApi {


    @PUT(BuildConfig.API_isactive)
    @FormUrlEncoded
    Call<UpdateReasponce> isactive(
            @Field("product_id") String product_id, @Field("isActive") String isActive, @Field("client_id") String client_id);


    @POST(BuildConfig.API_PRODUCTSEARCH_FROM_INVENTORY_LIST)
    @FormUrlEncoded
    Call<ProductFromInventoryListResponse> getProductSearch(
            @Field("category_ids") String category_ids, @Field("mobile") String mobile, @Field("brand_id") String brand_id, @Field("search_phrase") String search_phrase, @Field("page") String page, @Field("vendor_active") String vendor_active);
}
