package com.rmart.retiler.product.api;

import com.google.gson.JsonObject;
import com.rmart.BuildConfig;
import com.rmart.retiler.product.model.brand.BrandListResponse;
import com.rmart.retiler.product.model.category.CategoryListResponse;
import com.rmart.retiler.product.model.product.addproduct.AddProductResponse;
import com.rmart.retiler.product.model.product.ProductListResponse;
import com.rmart.retiler.product.model.subCategory.SubCategoryListResponse;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIUnitMeasureListResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetailerProductDetailsApi {
    @POST(BuildConfig.API_CATEGORY_LIST)
    @FormUrlEncoded
    Call<CategoryListResponse> getAllCategories(@Field("start_index") String startIndex, @Field("end_index") String endIndex,
                                                @Field("category_id") String categoryId);
    @POST(BuildConfig.API_SUB_CATEGORY_PRODUCT_LIST)
    @FormUrlEncoded
    Call<SubCategoryListResponse> getAllSubCategories(@Field("start_index") String startIndex, @Field("end_index") String endIndex,
                                                      @Field("sub_category_id") String subCategoryId);
    @POST(BuildConfig.API_PRODUCT_LIST)
    @FormUrlEncoded
    Call<ProductListResponse> getAllProducts(@Field("start_index") String startIndex, @Field("end_index") String endIndex,
                                             @Field("mobile") String mobile, @Field("stock_type") String stockType);

    @POST(BuildConfig.API_BRAND_LIST)
    @FormUrlEncoded
    Call<BrandListResponse> getBrandList(@Field("start_index") String startIndex, @Field("end_index") String endIndex, @Field("brand_id") String brandId);

//https://martapi.rokad.in/api/Products/add_product
 //   https://it.rokad.in/api/products/add_product_Test
    @POST(BuildConfig.API_ADD_PRODUCT)
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<AddProductResponse> addProduct(@Body JsonObject rawJsonData);

    @GET(BuildConfig.API_UNITIES)
    Call<APIUnitMeasureListResponse> getAPIUnitMeasureList();

    @GET(BuildConfig.API_STOCKE)
    Call<APIStockListResponse> getAPIStockList();
}

