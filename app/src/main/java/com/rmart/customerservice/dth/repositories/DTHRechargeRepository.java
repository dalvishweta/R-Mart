package com.rmart.customerservice.dth.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.dth.api.DthService;
import com.rmart.customerservice.dth.model.DthResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DTHRechargeRepository {

    public static MutableLiveData<DthResponse> getCustomerInfo(String vc_number, String operator) {

        DthService dthRechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(DthService.class);
        final MutableLiveData<DthResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<DthResponse> call = dthRechargeService.getCustomerIdInfo(vc_number,operator);
        final DthResponse result = new DthResponse();

        call.enqueue(new Callback<DthResponse>() {
            @Override
            public void onResponse(Call<DthResponse> call, Response<DthResponse> response) {

                if( response.isSuccessful()) {

                    DthResponse data = response.body();
                    resultMutableLiveData.setValue(data);

                } else {

                    String a= response.message();
                    result.setMsg(a);
                    result.setStatus(response.code());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<DthResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setStatus(400);
                resultMutableLiveData.setValue(result);
           }
        });
        return resultMutableLiveData;
    }
    public static MutableLiveData<DthResponse> getCustomerInfoMobile(String vc_number, String operator) {

        DthService dthRechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(DthService.class);
        final MutableLiveData<DthResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<DthResponse> call = dthRechargeService.getCustomerIdInfoMobile(vc_number,operator);
        final DthResponse result = new DthResponse();

        call.enqueue(new Callback<DthResponse>() {
            @Override
            public void onResponse(Call<DthResponse> call, Response<DthResponse> response) {

                if( response.isSuccessful()) {

                    DthResponse data = response.body();
                    resultMutableLiveData.setValue(data);

                } else {

                    String a= response.message();
                    result.setMsg(a);
                    result.setStatus(response.code());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<DthResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setStatus(400);
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;
    }


}
