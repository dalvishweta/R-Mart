package com.rmart.wallet.viewmodel.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rmart.profile.model.MyProfile;
import com.rmart.wallet.api.WalletTransction;
import com.rmart.wallet.model.WalletResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletRepository {

    public static MutableLiveData<WalletResponse> getWalletInfo(Context context) {

        WalletTransction walletsevice = RetrofitClientInstance.getRetrofitInstance().create(WalletTransction.class);
        final MutableLiveData<WalletResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<WalletResponse> call = walletsevice.getWalletInfo(MyProfile.getInstance(context).getUserID(),MyProfile.getInstance(context).getWallet_id());
        final WalletResponse result = new WalletResponse();

        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {

                if( response.isSuccessful()) {

                    WalletResponse data = response.body();
                    resultMutableLiveData.setValue(data);

                } else {

                    String a= response.message();
                    result.setMsg(a);
                    result.setStatus(response.code());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setStatus(400);
                resultMutableLiveData.setValue(result);
           }
        });
        return resultMutableLiveData;
    }


}
