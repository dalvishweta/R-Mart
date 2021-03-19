package com.rmart.electricity.billdetails.repositoris;

import com.rmart.BuildConfig;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.electricity.fetchbill.model.ElecProcessPOJO;
import com.rmart.electricity.rsakeyResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessRSAKeyRepository {

    public static MutableLiveData<rsakeyResponse> getElecticityProcessRsaKey(String trnAmount,String serviceID,String OrderID){
        ElecticityService electicityService =  RetrofitClientInstance.getRetrofitInstance().create(ElecticityService.class);
        final MutableLiveData<rsakeyResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<rsakeyResponse> call = electicityService.electicityProcessRsaKey(MyProfile.getInstance().getUserID(),trnAmount,serviceID,"Electricity",OrderID);
        final rsakeyResponse result = new rsakeyResponse();

        call.enqueue(new Callback<rsakeyResponse>() {
            @Override
            public void onResponse(Call<rsakeyResponse> call, Response<rsakeyResponse> response) {
                rsakeyResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<rsakeyResponse> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Internet Connection");
                }
                else {
                    result.setMsg(t.getLocalizedMessage());
                }
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;

    }

}
