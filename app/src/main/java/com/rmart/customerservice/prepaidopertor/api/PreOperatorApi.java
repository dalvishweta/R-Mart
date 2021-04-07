package com.rmart.customerservice.prepaidopertor.api;

import com.rmart.BuildConfig;
import com.rmart.customerservice.prepaidopertor.model.PreOperatorBaseResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;


public interface PreOperatorApi {

    @GET(BuildConfig.PrePaidOperator)
    @FormUrlEncoded


    Call<PreOperatorBaseResponse> getPreOPeratorList();
}
