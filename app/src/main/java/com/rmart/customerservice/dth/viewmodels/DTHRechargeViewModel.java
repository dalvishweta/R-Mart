package com.rmart.customerservice.dth.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.customerservice.dth.model.DthResponse;
import com.rmart.customerservice.dth.repositories.DTHRechargeRepository;

public class DTHRechargeViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<DthResponse> responseVRechargeMutableLiveData = new MutableLiveData<>();



    public void doDTHRecharge(String vc_number,String operator){
        isLoading.setValue(true);
        DTHRechargeRepository.getCustomerInfo(vc_number, operator).observeForever(getCustomerInfoResponse -> {
            responseVRechargeMutableLiveData.setValue(getCustomerInfoResponse);
            isLoading.setValue(false);
        });

    }
}
