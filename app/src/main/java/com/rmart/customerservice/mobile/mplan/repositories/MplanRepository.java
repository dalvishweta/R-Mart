package com.rmart.customerservice.mobile.mplan.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.mobile.mplan.api.MplanListApi;
import com.rmart.customerservice.mobile.mplan.model.MplanBaseResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MplanRepository {

    public static MutableLiveData<MplanBaseResponse> getPlanList(String operator, String cricle, String service_type, String mobileapp,String mobileversionid,String mobile_no){

        MplanListApi planListApi = RetrofitClientInstance.getRetrofitInstance().create(MplanListApi.class);
        final MutableLiveData<MplanBaseResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<MplanBaseResponse> call = planListApi.getPlan(operator,cricle,service_type,mobileapp,mobileversionid,mobile_no);

        call.enqueue(new Callback<MplanBaseResponse>() {
            @Override
            public void onResponse(Call<MplanBaseResponse> call, Response<MplanBaseResponse> response) {
                MplanBaseResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<MplanBaseResponse> call, Throwable t) {
                final MplanBaseResponse result = new MplanBaseResponse();
                if(t.getLocalizedMessage().contains("hostname"))
                {
                    result.setStatus("Please Check Internet Connection");

                } else {
                    result.setStatus(t.getMessage());
                }
                result.setStatus("Failure");
                resultMutableLiveData.setValue(result);
            }
        });


        return resultMutableLiveData;

    }


}
