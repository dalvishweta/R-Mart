package com.rmart.customerservice.mobile.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.models.mobileRecharge.RechargeBaseClass;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargeRepository {

    public final static int SERVICE_TYPE_MOBILE_RECHARGE=1;
    public final static int SERVICE_TYPE_DTH_RECHARGE=2;
    public final static int RECHARGE_TYPE_PREPAID_RECHARGE=1;
    public final static int RECHARGE_TYPE_POSTPAID_RECHARGE=2;
    public final static int PLAN_TYPE_SPECIAL_RECHARGE=1;
    public final static int PLAN_TYPE_REGULAR_RECHARGE=0;



    public static MutableLiveData<RechargeBaseClass> doMobileRecharge(int serviceType, String customerNumber, int rechargeType, String operator,
                                                                            String location, String mobileNumber, int planType, String rechargeAmount, String userId, String ccavneuData) {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstance().create(MobileRechargeService.class);
        final MutableLiveData<RechargeBaseClass> resultMutableLiveData = new MutableLiveData<>();
        Call<RechargeBaseClass> call = mobileRechargeService.recharge(serviceType,  customerNumber, rechargeType,operator, location,mobileNumber,planType,rechargeAmount,userId,ccavneuData);
        call.enqueue(new Callback<RechargeBaseClass>() {
            @Override
            public void onResponse(Call<RechargeBaseClass> call, Response<RechargeBaseClass> response) {

                if(response.isSuccessful()) {
                    RechargeBaseClass data = response.body();
                    resultMutableLiveData.setValue(data);
                } else {
                    final RechargeBaseClass result = new RechargeBaseClass();

                    result.setStatus(response.code());
                    result.setMsg(response.message());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<RechargeBaseClass> call, Throwable t) {
                final RechargeBaseClass result = new RechargeBaseClass();
                result.setStatus(400);
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
