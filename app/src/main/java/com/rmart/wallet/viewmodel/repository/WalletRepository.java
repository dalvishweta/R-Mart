package com.rmart.wallet.viewmodel.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rmart.electricity.RSAKeyResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.wallet.api.WalletTransction;
import com.rmart.wallet.model.CheckWalletTopup;
import com.rmart.wallet.model.WalletResponse;

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

                }  else {

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

    public static MutableLiveData<RSAKeyResponse> getRSAKey(String user_id,String wallet_id ,String txt_amount) {

        WalletTransction walletRechargeService = RetrofitClientInstance.getRetrofitInstanceForAddP().create(WalletTransction.class);
        final MutableLiveData<RSAKeyResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<RSAKeyResponse> call = walletRechargeService.RsaKeyVRecharge(user_id,wallet_id,txt_amount, "27", "wallet-topup");
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

    public static MutableLiveData<CheckWalletTopup> doWalletRecharge(String user_id,String wallet_id ,String data) {

        WalletTransction walletRechargeService = RetrofitClientInstance.getRetrofitInstanceForAddP().create(WalletTransction.class);
        final MutableLiveData<CheckWalletTopup> resultMutableLiveData = new MutableLiveData<>();
        Call<CheckWalletTopup> call = walletRechargeService.CheckWalletTopup(user_id,wallet_id,data);
        final CheckWalletTopup result = new CheckWalletTopup();

        call.enqueue(new Callback<CheckWalletTopup>() {
            @Override
            public void onResponse(Call<CheckWalletTopup> call, Response<CheckWalletTopup> response) {
                CheckWalletTopup data = response.body();
                if(data!=null && data.getData()!=null) {
                    resultMutableLiveData.setValue(data);
                } else {

                    result.setStatus("failed");
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<CheckWalletTopup> call, Throwable t) {
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
