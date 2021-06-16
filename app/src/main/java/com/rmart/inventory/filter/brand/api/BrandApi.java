package com.rmart.inventory.filter.brand.api;

import com.rmart.BuildConfig;
import com.rmart.inventory.filter.brand.models.ProductBrandReponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BrandApi {
    @POST(BuildConfig.INVENTORY_FILTER_BRAND)
    @FormUrlEncoded
    Call<ProductBrandReponse> getProductBrandInfo(@Field("start_index") String start_index,@Field("end_index") String end_index,@Field("brand_id") String brandId,@Field("vendor_id") String vendor_id,@Field("status") String status);
}
