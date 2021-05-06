package com.rmart.customerservice.mobile.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.models.mobileRecharge.MobileRechargeBaseClass;
import com.rmart.electricity.RSAKeyResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargeRepository {


    public static MutableLiveData<MobileRechargeBaseClass> doMobileRecharge(int service_type, String customer_number, int rechargetype, String Operator, String PostOperator,
                                                                            String Location, String Mobile_number, int plan_type, String Recharge_amount, String user_id, String ccavneuData) {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(MobileRechargeService.class);
        final MutableLiveData<MobileRechargeBaseClass> resultMutableLiveData = new MutableLiveData<>();
        Call<MobileRechargeBaseClass> call = mobileRechargeService.do_mobile_Recharge(service_type,  customer_number, rechargetype,Operator, Location,Mobile_number,plan_type,Recharge_amount, user_id, ccavneuData);
        final MobileRechargeBaseClass result = new MobileRechargeBaseClass();

        call.enqueue(new Callback<MobileRechargeBaseClass>() {
            @Override
            public void onResponse(Call<MobileRechargeBaseClass> call, Response<MobileRechargeBaseClass> response) {
                MobileRechargeBaseClass data = response.body();
                if(response.isSuccessful()) {
                    resultMutableLiveData.setValue(data);
                } else {

                    result.setStatus(400);
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<MobileRechargeBaseClass> call, Throwable t) {
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


    public static MutableLiveData<RSAKeyResponse> getRSAKey(String user_id, String txt_amount, String service_id, String service_name) {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstance().create(MobileRechargeService.class);
        final MutableLiveData<RSAKeyResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<RSAKeyResponse> call = mobileRechargeService.RsaKeyVRecharge(user_id,txt_amount, service_id, service_name);
        final RSAKeyResponse result = new RSAKeyResponse();

        call.enqueue(new Callback<RSAKeyResponse>() {
            @Override
            public void onResponse(Call<RSAKeyResponse> call, Response<RSAKeyResponse> response) {
                RSAKeyResponse data = response.body();
                if(data!=null && data.getData()!=null) {
                    resultMutableLiveData.setValue(data);
                } else {

                    result.setStatus("failed");
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<RSAKeyResponse> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"hungryindia.co.in\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Internet Connection");
                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                result.setStatus("failed");
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;
    }

}
