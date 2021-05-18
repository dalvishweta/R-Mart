package com.rmart.customerservice.dth.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.BuildConfig;
import com.rmart.customerservice.dth.model.DthResponse;
import com.rmart.customerservice.dth.repositories.DTHRechargeRepository;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.customerservice.mobile.repositories.MobileRechargeRepository;
import com.rmart.electricity.RSAKeyResponse;
import com.rmart.profile.model.MyProfile;

public class DTHRechargeMakePaymentViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Operator> operatorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DthResponse> dthPOJOMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> cumsumerNumber = new MutableLiveData<>();
    public MutableLiveData<String> errorCumsumerAmount = new MutableLiveData<>();
    public MutableLiveData<String> cumsumerAmount = new MutableLiveData<>();
    public MutableLiveData<RSAKeyResponse> responseRsakeyMutableLiveData = new MutableLiveData<>();

    public void onClick(final View view) {


        if(cumsumerAmount.getValue()!=null && Double.parseDouble(cumsumerAmount.getValue())>0) {
            errorCumsumerAmount.setValue(null);
            getRsaKey(MyProfile.getInstance(view.getContext()).getUserID());
        } else {
            errorCumsumerAmount.setValue("Please Enter Recharge Amount");
        }
    }

    public void getRsaKey(String user_id){
        isLoading.setValue(true);
        MobileRechargeRepository.getRSAKey(user_id, String.valueOf(cumsumerAmount.getValue()), BuildConfig.service_id,BuildConfig.service_name).observeForever(responseKeyResponse -> {

            responseRsakeyMutableLiveData.setValue(responseKeyResponse);

        });

    }




}
