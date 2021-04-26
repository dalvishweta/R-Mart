package com.rmart.electricity.billdetails.repositoris;

import android.content.Context;

import com.rmart.BuildConfig;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.electricity.RSAKeyResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessRSAKeyRepository {

    public static MutableLiveData<RSAKeyResponse> getElecticityProcessRsaKey(Context context, String trnAmount, String serviceID, String OrderID){
        ElecticityService electicityService =  RetrofitClientInstance.getRetrofitInstance().create(ElecticityService.class);
        final MutableLiveData<RSAKeyResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<RSAKeyResponse> call = electicityService.electicityProcessRsaKey(MyProfile.getInstance(context).getUserID(),trnAmount,serviceID,"Electricity",OrderID);
        final RSAKeyResponse result = new RSAKeyResponse();

        call.enqueue(new Callback<RSAKeyResponse>() {
            @Override
            public void onResponse(Call<RSAKeyResponse> call, Response<RSAKeyResponse> response) {
                RSAKeyResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<RSAKeyResponse> call, Throwable t) {
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
