package com.rmart.customerservice.mobile.viewmodels;

import com.rmart.electricity.CCAvenueResponceModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentStatusViewModel extends ViewModel {

    public MutableLiveData<CCAvenueResponceModel> ccAvenueResponceModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> messageType = new MutableLiveData<>();
    public MutableLiveData<String> mobileNumberMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> nameStringMutableLiveData = new MutableLiveData<>();
    // TODO: Implement the ViewModel
}