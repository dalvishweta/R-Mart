package com.rmart.electricity.billdetails.repositoris;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.electricity.billdetails.model.BillDataBaseClass;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillPaymentRepository {

    public static MutableLiveData<BillDataBaseClass> doElectricityBillPayment(String user_id ,String operator, String ConsumerID, String bill_unit, String mobile_number,
                                                                      String amount, String ConsumerName, String Orderid, String ccavenuedata) {

        ElecticityService electricityRechargeService = RetrofitClientInstance.getRetrofitInstance().create(ElecticityService.class);
        final MutableLiveData<BillDataBaseClass> resultMutableLiveData = new MutableLiveData<>();
        /*Call<BillDataBaseClass> call = electricityRechargeService.electicitybillPayment(user_id,  operator, ConsumerID,bill_unit,mobile_number,amount,ConsumerName,Orderid,ccavenuedata);
        call.enqueue(new Callback<BillDataBaseClass>() {
            @Override
            public void onResponse(Call<BillDataBaseClass> call, Response<BillDataBaseClass> response) {

                Gson s = new Gson();
                if(response.isSuccessful()) {
                    BillDataBaseClass data = response.body();
                    data.setStatus(response.code());
                    Log.d("Recharge",s.toJson(data));
                    resultMutableLiveData.setValue(data);
                } else {
                    final BillDataBaseClass result = new BillDataBaseClass();
                    result.setStatus(response.code());
                    result.setMsg(response.message());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<BillDataBaseClass> call, Throwable t) {
                final BillDataBaseClass result = new BillDataBaseClass();
                result.setStatus(400);
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"hungryindia.co.in\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Internet Connection");
                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                resultMutableLiveData.setValue(result);
            }
        });*/
        return resultMutableLiveData;
    }



}
