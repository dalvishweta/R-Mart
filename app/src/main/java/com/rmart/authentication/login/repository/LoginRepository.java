package com.rmart.authentication.login.repository;

import com.rmart.BuildConfig;
import com.rmart.authentication.login.api.LoginService;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.customerservice.dth.api.DthService;
import com.rmart.customerservice.dth.model.DthResponse;
import com.rmart.utilits.RetrofitClientInstance;


import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class LoginRepository {
    public static MutableLiveData<LoginResponse> getLoginInfo(String mobile_number,String role_id) {
        LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
        final MutableLiveData<LoginResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<LoginResponse> call = loginService.getLoginInfo(mobile_number,role_id);
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

}
