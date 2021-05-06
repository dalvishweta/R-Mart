package com.rmart.customerservice.mobile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.repositories.MobileRechargeRepository;

public class MobileRechargeViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<ResponseGetHistory> responseGetHistoryMutableLiveData = new MutableLiveData<>();



    public void getHistory(String userId){
        isLoading.setValue(true);
        MobileRechargeRepository.getHistory(userId).observeForever(responseGetHistory -> {
            responseGetHistoryMutableLiveData.setValue(responseGetHistory);
            isLoading.setValue(false);
        });

    }

}
