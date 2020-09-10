package com.rmart.utilits.services;

import com.google.gson.JsonObject;
import com.rmart.BuildConfig;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.views.AddProductToInventory;
import com.rmart.inventory.views.InventoryActivity;
import com.rmart.utilits.pojos.AddProductToInventoryResponse;
import com.rmart.utilits.pojos.ProductListResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.pojos.ShowProductResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VendorInventoryService {

    @POST(BuildConfig.VENDOR_INVENTORY_ADD_PRODUCT)
    Call<AddProductToInventoryResponse> addProductToInventory(@Body JsonObject productResponse);

    @POST(BuildConfig.VENDOR_INVENTORY_EDIT_PRODUCT)
    Call<AddProductToInventoryResponse> editProductToInventory(@Body JsonObject productResponse);

    @POST(BuildConfig.VENDOR_INVENTORY_GET_PRODUCT_LIST)
    @FormUrlEncoded
    Call<ProductListResponse> getProductList(@Field("start_index") String startIndex,
                                             @Field("mobile") String mobile,
                                             @Field("stock_type") String stockType,
                                             @Field("category_id") String categoryID);

    @POST(BuildConfig.VENDOR_INVENTORY_GET_PRODUCT)
    @FormUrlEncoded
    Call<ShowProductResponse> getProduct(@Field("product_id") String productID,
                                         @Field("user_id") String userID);
}
