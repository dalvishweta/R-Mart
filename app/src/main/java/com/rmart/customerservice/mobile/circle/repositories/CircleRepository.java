package com.rmart.customerservice.mobile.circle.repositories;

import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.circle.model.CircleResponse;
import com.rmart.customerservice.mobile.operators.model.OperatorResponse;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CircleRepository {

    public static MutableLiveData<CircleResponse> getCircles() {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(MobileRechargeService.class);
        final MutableLiveData<CircleResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<CircleResponse> call = mobileRechargeService.circles();
        final CircleResponse result = new CircleResponse();

        call.enqueue(new Callback<CircleResponse>() {
            @Override
            public void onResponse(Call<CircleResponse> call, Response<CircleResponse> response) {
                CircleResponse data = response.body();
                resultMutableLiveData.setValue(data);

            }

            @Override
            public void onFailure(Call<CircleResponse> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"hungryindia.co.in\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Internet Connection");
                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;
    }
}
