package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.utilits.pojos.APIProductListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProductService {
    @FormUrlEncoded
    @POST(BuildConfig.API_PRODUCT_LIST)
    Call<APIProductListResponse> getAPIProducts(@Field("start_index") String startIndex,
                                                @Field("end_index") String endIndex);
}
