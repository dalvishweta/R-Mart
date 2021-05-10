package com.rmart.customerservice.mobile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.customerservice.mobile.models.mobileRecharge.RechargeBaseClass;

public class PaymentStatusViewModel extends ViewModel {

    public MutableLiveData<RechargeBaseClass> rechargeBaseClassMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> mobileNumberMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> nameStringMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> amount = new MutableLiveData<>();


    // TODO: Implement the ViewModel
}