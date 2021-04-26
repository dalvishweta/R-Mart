package com.rmart.customerservice.dth.api;

import com.rmart.BuildConfig;
import com.rmart.customerservice.dth.model.DthResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DthService {

    @GET(BuildConfig.DTH_CustomerIdInfo)
    Call<DthResponse> getCustomerIdInfo(@Query("vcnumber") String vcNumber, @Query("operator") String Operator);
}
