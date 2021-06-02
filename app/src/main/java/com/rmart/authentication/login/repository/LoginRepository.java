package com.rmart.authentication.login.repository;

import androidx.lifecycle.MutableLiveData;

import com.rmart.authentication.login.api.LoginService;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    public static MutableLiveData<LoginResponse> getLoginInfo(String mobile_number,String role_id) {
        LoginService loginService = RetrofitClientInstance.getRetrofitInstanceForAddP().create(LoginService.class);
        final MutableLiveData<LoginResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<LoginResponse> call = loginService.getLoginInfo(mobile_number,role_id,"2");
        final LoginResponse result = new LoginResponse();

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if( response.isSuccessful()) {

                    LoginResponse data = response.body();
                    resultMutableLiveData.setValue(data);

                } else {
                   String a= response.message();
                    result.setMsg(a);
                    result.setCode(response.code());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setCode(400);
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;
    }

    public static MutableLiveData<com.rmart.utilits.pojos.LoginResponse>VerifyOTP(String mobile_number,String role_id,String otp,String device_id) {
        LoginService loginService = RetrofitClientInstance.getRetrofitInstanceForAddP().create(LoginService.class);
        final MutableLiveData<com.rmart.utilits.pojos.LoginResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<com.rmart.utilits.pojos.LoginResponse> call = loginService.VerifyOTP(mobile_number,role_id,otp,device_id);
        final com.rmart.utilits.pojos.LoginResponse result = new com.rmart.utilits.pojos.LoginResponse();

        call.enqueue(new Callback<com.rmart.utilits.pojos.LoginResponse>() {
            @Override
            public void onResponse(Call<com.rmart.utilits.pojos.LoginResponse> call, Response<com.rmart.utilits.pojos.LoginResponse> response) {

                if( response.isSuccessful()) {

                    com.rmart.utilits.pojos.LoginResponse data = response.body();
                    resultMutableLiveData.setValue(data);

                } else {

                    String a= response.message();
                    result.setMsg(a);
                    result.setStatus(response.message());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<com.rmart.utilits.pojos.LoginResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setStatus(result.getMsg());
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;
    }

}
