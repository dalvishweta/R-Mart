package com.rmart.customerservice.mobile.mplan.api;

import com.rmart.BuildConfig;
import com.rmart.customerservice.mobile.models.mPlans.PostPaidResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MplanListApi {

    @GET(BuildConfig.GET_PLANS)
    Call<ResponseGetPlans> getPlan(@Query("operator") String operator, @Query("cricle") String cricle, @Query("service_type") String brandId,
                                   @Query("mobileapp") String vendor_id, @Query("mobileversionid") String mobileversionid, @Query("mobile_no") String mobile_no);
    @GET(BuildConfig.GET_PLANS)
    Call<PostPaidResponseGetPlans> getPostPaidPlan(@Query("operator") String operator, @Query("cricle") String cricle, @Query("service_type") String brandId,
                                                   @Query("mobileapp") String vendor_id, @Query("mobileversionid") String mobileversionid, @Query("mobile_no") String mobile_no);
}
