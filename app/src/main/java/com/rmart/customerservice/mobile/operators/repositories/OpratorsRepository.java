package com.rmart.customerservice.mobile.operators.repositories;

import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.operators.model.OperatorResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpratorsRepository {

    public static MutableLiveData<OperatorResponse> getOperators(String type) {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(MobileRechargeService.class);
        final MutableLiveData<OperatorResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<OperatorResponse> call = mobileRechargeService.operators(type);
        final OperatorResponse result = new OperatorResponse();

        call.enqueue(new Callback<OperatorResponse>() {
            @Override
            public void onResponse(Call<OperatorResponse> call, Response<OperatorResponse> response) {
                OperatorResponse data = response.body();
                resultMutableLiveData.setValue(data);

            }

            @Override
            public void onFailure(Call<OperatorResponse> call, Throwable t) {
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
