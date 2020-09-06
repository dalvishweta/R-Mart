package com.rmart.utilits.services;

import com.google.gson.JsonObject;
import com.rmart.BuildConfig;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.views.AddProductToInventory;
import com.rmart.utilits.pojos.AddProductToInventoryResponse;
import com.rmart.utilits.pojos.ProductResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VendorInventoryService {

    @POST(BuildConfig.VENDOR_INVENTORY_ADD_PRODUCT)
    Call<AddProductToInventoryResponse> addProductToInventory(@Body JsonObject productResponse);

}
