package com.rmart.authentication.registration.repository;


import androidx.lifecycle.MutableLiveData;

import com.rmart.authentication.registration.api.RegisterService;
import com.rmart.authentication.registration.model.RegisterResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    public static MutableLiveData<RegisterResponse> getRegistration(String first_name,String last_name,String gender,String email_id,String mobile_number,String role_id,String client_id,String refer_no,String user_id,String device_id) {
       RegisterService registerService = RetrofitClientInstance.getRetrofitInstanceForAddP().create(RegisterService.class);
        final MutableLiveData<RegisterResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<RegisterResponse> call = registerService.getRegistration(first_name,last_name,gender,email_id,mobile_number,role_id,client_id,refer_no,user_id,device_id);
        final RegisterResponse result = new RegisterResponse();
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse data = response.body();
                    resultMutableLiveData.setValue(data);

                } else {

                    String a = response.message();
                    result.setMsg(a);
                    result.setCode(response.code());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setCode(400);
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;
    }

}
