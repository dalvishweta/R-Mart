package com.rmart.authentication.registration.api;

import com.rmart.BuildConfig;
import com.rmart.authentication.registration.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {
    @POST(BuildConfig.NEW_REGISTRATION)
    @FormUrlEncoded
    Call<RegisterResponse> getRegistration(@Field("full_name") String Full_Name,@Field("last_name") String Last_Name,@Field("gender") String Gender,@Field("email") String Email,@Field("mobile_number") String Mobile_Number,
                                           @Field("roll") String Roll,@Field("client_id") String Client_ID,@Field("refer_no")String refer_no,@Field("refer_user_id")String refer_user_id,@Field("device_id") String device_id);
}
