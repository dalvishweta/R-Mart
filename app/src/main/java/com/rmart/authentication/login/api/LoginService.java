package com.rmart.authentication.login.api;

import com.rmart.BuildConfig;
import com.rmart.authentication.login.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST(BuildConfig.NEW_LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> getLoginInfo(@Field("username") String Username,@Field("role_id") String Role_id,@Field("client_id") String client_id);

    @POST(BuildConfig.VERIFY_OTP)
    @FormUrlEncoded
    Call<com.rmart.utilits.pojos.LoginResponse> VerifyOTP(@Field("username") String username,
                                                      @Field("role_id") String role_id,@Field("otp") String otp,@Field("device_id") String deviceKey);

}
