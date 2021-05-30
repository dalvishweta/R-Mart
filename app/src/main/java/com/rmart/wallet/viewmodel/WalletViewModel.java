package com.rmart.wallet.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.wallet.model.WalletResponse;
import com.rmart.wallet.viewmodel.repository.WalletRepository;

public class WalletViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<WalletResponse> walletResponseMutableLiveData = new MutableLiveData<>();



    public void walletTransaction(){
        isLoading.setValue(true);
        WalletRepository.getWalletInfo().observeForever(Result -> {
            walletResponseMutableLiveData.setValue(Result);
            isLoading.postValue(false);
        });


    }

}
