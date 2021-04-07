package com.rmart.customerservice.postpaidopertor.api;

import com.rmart.BuildConfig;
import com.rmart.customerservice.postpaidopertor.model.PostOperatorBaseResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;


public interface PostPaidOperatorApi {

    @GET(BuildConfig.PostPaidOperator)
    @FormUrlEncoded


    Call<PostOperatorBaseResponse> getPostOPeratorList();
}
