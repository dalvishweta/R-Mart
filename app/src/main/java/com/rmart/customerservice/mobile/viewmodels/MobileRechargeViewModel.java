package com.rmart.customerservice.mobile.viewmodels;

import com.rmart.customer.dashboard.model.HomePageResponse;
import com.rmart.customer.dashboard.repositories.ShopRepository;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.repositories.MobileRechargeRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MobileRechargeViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<ResponseGetHistory> responseGetHistoryMutableLiveData = new MutableLiveData<>();


    public void loadShopHomePage(){
        isLoading.setValue(true);
        MobileRechargeRepository.getHistory().observeForever(responseGetHistory -> {
            responseGetHistoryMutableLiveData.postValue(responseGetHistory);
            isLoading.postValue(false);
        });




    }

}
