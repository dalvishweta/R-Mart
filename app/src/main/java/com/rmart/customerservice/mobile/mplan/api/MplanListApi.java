package com.rmart.customerservice.mobile.mplan.api;

import com.rmart.BuildConfig;
import com.rmart.customerservice.mobile.mplan.model.MplanBaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface MplanListApi {

    @POST(BuildConfig.GET_PLANS)
    @FormUrlEncoded


    Call<MplanBaseResponse> getPlan(@Field("operator") String operator, @Field("cricle") String cricle, @Field("service_type") String brandId,
                                    @Field("mobileapp") String vendor_id, @Field("mobileversionid") String mobileversionid, @Field("mobile_no") String mobile_no);
}
