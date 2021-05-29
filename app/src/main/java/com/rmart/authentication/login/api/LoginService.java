package com.rmart.authentication.login.api;

import com.rmart.BuildConfig;
import com.rmart.authentication.login.model.LoginResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET(BuildConfig.DTH_CustomerIdInfo)
    Call<LoginResponse> getLoginInfo(@Query("username") String Username, @Query("role_id") String Role_id);
}
