package com.rmart.electricity.fetchbill.repositoris;

import com.rmart.BuildConfig;
import com.rmart.electricity.fetchbill.model.ElecProcessPOJO;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchBillRepository {

    public static MutableLiveData<ElecProcessPOJO> getBillDetails(String operator,String consumerno,String unit,String mobileno){

        ElecticityService electicityService = RetrofitClientInstance.getInstance().getRetrofitInstanceRokad().create(ElecticityService.class);
        final MutableLiveData<ElecProcessPOJO> resultMutableLiveData = new MutableLiveData<>();
        Call<ElecProcessPOJO> call = electicityService.electicityProcess(MyProfile.getInstance().getUserID(), operator,consumerno , unit,
                mobileno);
        final ElecProcessPOJO result = new ElecProcessPOJO();

        call.enqueue(new Callback<ElecProcessPOJO>() {
            @Override
            public void onResponse(Call<ElecProcessPOJO> call, Response<ElecProcessPOJO> response) {
                ElecProcessPOJO data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ElecProcessPOJO> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Enternet Connection");
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
