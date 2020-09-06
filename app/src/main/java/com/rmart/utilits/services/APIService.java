package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.utilits.pojos.APIBrandListResponse;
import com.rmart.utilits.pojos.APIBrandResponse;
import com.rmart.utilits.pojos.APICategoryListResponse;
import com.rmart.utilits.pojos.APIProductListResponse;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIUnitMeasureListResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET(BuildConfig.API_UNITIES)
    Call<APIUnitMeasureListResponse> getAPIUnitMeasureList();

    @GET(BuildConfig.API_BRANDS)
    Call<APIBrandListResponse> getAPIBrandList();

    @GET(BuildConfig.API_STOCKE)
    Call<APIStockListResponse> getAPIStockList();

    @GET(BuildConfig.API_CATEGORIES)
    Call<APICategoryListResponse> getAPICategoryList();

    @POST(BuildConfig.API_PRODUCT_LIST)
    @FormUrlEncoded
    Call<APIProductListResponse> getAPIProducts(@Field("start_index") String startIndex,
                                                @Field("end_index") String endIndex);
}
