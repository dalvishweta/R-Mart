package com.rmart.wallet.billing_history.repository;

import com.rmart.customerservice.dth.model.DthResponse;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.wallet.billing_history.api.BillingService;
import com.rmart.wallet.billing_history.models.BillingResponse;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingRepository {
    public static MutableLiveData<BillingResponse> getBillingHistory(String from_date, String to_date, String user_id){
        BillingService billingService = RetrofitClientInstance.getRetrofitInstance().create(BillingService.class);
        final MutableLiveData<BillingResponse> responseMutableLiveData = new MutableLiveData<>();

        Call<BillingResponse> call = billingService.getBillingHistoryInfo(from_date,to_date,user_id);
        final BillingResponse result = new BillingResponse();
        call.enqueue(new Callback<BillingResponse>() {
            @Override
            public void onResponse(Call<BillingResponse> call, Response<BillingResponse> response) {
                if( response.isSuccessful()) {

                    BillingResponse data = response.body();

                    responseMutableLiveData.setValue(data);

                } else {

                    String a= response.message();
                    result.setMsg(a);
                    result.setStatus(response.code());
                    responseMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<BillingResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setStatus(400);
                responseMutableLiveData.setValue(result);
            }
        });
        return responseMutableLiveData;

    }
}
