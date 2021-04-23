package com.rmart.customerservice.mobile.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.models.MRechargeBaseClass;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileRechargeRepository {

    public static MutableLiveData<ResponseGetHistory> getHistory() {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(MobileRechargeService.class);
        final MutableLiveData<ResponseGetHistory> resultMutableLiveData = new MutableLiveData<>();
        Call<ResponseGetHistory> call = mobileRechargeService.getHistory("8614","25");
        final ResponseGetHistory result = new ResponseGetHistory();

        call.enqueue(new Callback<ResponseGetHistory>() {
            @Override
            public void onResponse(Call<ResponseGetHistory> call, Response<ResponseGetHistory> response) {
                ResponseGetHistory data = response.body();
                if(data!=null && data.getLastTransaction()!=null) {
                    resultMutableLiveData.setValue(data);
                 } else {
                    
                    result.setStatus("failed");
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetHistory> call, Throwable t) {
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


    public static MutableLiveData<MRechargeBaseClass> getVRecharge(int service_type,String preOperator_dth,String customer_number,String recharge_type,String preOperator,String PostOperator,
                                                                   String Location,String Mobile_number,String rechargeType,String Recharge_amount, String user_id,String ccavneuData) {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(MobileRechargeService.class);
        final MutableLiveData<MRechargeBaseClass> resultMutableLiveData = new MutableLiveData<>();
        Call<MRechargeBaseClass> call = mobileRechargeService.VRecharge(service_type, preOperator_dth, customer_number, recharge_type, preOperator, PostOperator, Location, Mobile_number, rechargeType, Recharge_amount, user_id, ccavneuData);
        final MRechargeBaseClass result = new MRechargeBaseClass();

        call.enqueue(new Callback<MRechargeBaseClass>() {
            @Override
            public void onResponse(Call<MRechargeBaseClass> call, Response<MRechargeBaseClass> response) {
                MRechargeBaseClass data = response.body();
                if(data!=null && data.getData()!=null) {
                    resultMutableLiveData.setValue(data);
                } else {

                    result.setStatus("failed");
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<MRechargeBaseClass> call, Throwable t) {
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
