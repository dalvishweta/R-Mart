package com.rmart.customerservice.prepaidopertor.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.prepaidopertor.api.PreOperatorApi;
import com.rmart.customerservice.prepaidopertor.model.PreOperatorBaseResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreOperatorListRepository {

    public static MutableLiveData<PreOperatorBaseResponse> getPreOPeratorList(){

        PreOperatorApi preoperatorListApi = RetrofitClientInstance.getRetrofitInstance().create(PreOperatorApi.class);
        final MutableLiveData<PreOperatorBaseResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<PreOperatorBaseResponse> call = preoperatorListApi.getPreOPeratorList();

        call.enqueue(new Callback<PreOperatorBaseResponse>() {
            @Override
            public void onResponse(Call<PreOperatorBaseResponse> call, Response<PreOperatorBaseResponse> response) {
                PreOperatorBaseResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<PreOperatorBaseResponse> call, Throwable t) {
                final PreOperatorBaseResponse result = new PreOperatorBaseResponse();
                if(t.getLocalizedMessage().contains("hostname"))
                {
                    result.setMsg("Please Check Internet Connection");

                } else {
                    result.setMsg(t.getMessage());
                }
                result.setStatus("Failure");
                resultMutableLiveData.setValue(result);
            }
        });


        return resultMutableLiveData;

    }


}
