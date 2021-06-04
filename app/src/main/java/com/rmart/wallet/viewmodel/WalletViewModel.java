package com.rmart.wallet.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.electricity.RSAKeyResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.wallet.model.WalletResponse;
import com.rmart.wallet.viewmodel.repository.WalletRepository;

public class WalletViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<WalletResponse> walletResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<RSAKeyResponse> responseRsakeyMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> cumsumerAmount = new MutableLiveData<>();
    public MutableLiveData<String> errorCumsumerAmount = new MutableLiveData<>();

    public void onClick(final View view) {
        if(validate()) {
            errorCumsumerAmount.setValue(null);
            isLoading.postValue(true);
            getRsaKey(MyProfile.getInstance(view.getContext()).getUserID(),MyProfile.getInstance(view.getContext()).getWallet_id());

        } else {
            errorCumsumerAmount.setValue("Please Enter Recharge Amount");
            isLoading.postValue(false);
        }
    }

    public void walletTransaction(Context context){
        isLoading.setValue(true);
        WalletRepository.getWalletInfo(context).observeForever(Result -> {
            walletResponseMutableLiveData.setValue(Result);
            isLoading.postValue(false);
        });


    }

    public void getRsaKey(String user_id,String w_id){
        isLoading.setValue(true);
        WalletRepository.getRSAKey(user_id, w_id,String.valueOf(cumsumerAmount.getValue()) ).observeForever(responseKeyResponse -> {
            responseRsakeyMutableLiveData.setValue(responseKeyResponse);
            isLoading.postValue(false);
        });

    }

    public boolean validate() {
        boolean result = true;
        if (cumsumerAmount.getValue() == null || cumsumerAmount.getValue().isEmpty()) {
            errorCumsumerAmount.setValue("Please Enter Valid Amount");
            result = false;
        }

        return result;
    }

    public void onCumsumerNumberTextChanged(CharSequence s, int start, int before, int count) {
        errorCumsumerAmount.setValue(null);
    }
}
