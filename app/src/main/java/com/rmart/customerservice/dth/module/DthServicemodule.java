package com.rmart.customerservice.dth.module;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.R;
import com.rmart.customerservice.dth.actvities.DTHRechargeActivity;
import com.rmart.customerservice.dth.fragments.MakeDTHPayment;
import com.rmart.customerservice.dth.model.DthResponse;
import com.rmart.customerservice.dth.repositories.DTHRechargeRepository;
import com.rmart.customerservice.mobile.operators.model.Operator;


public class DthServicemodule extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Operator> operatorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DthResponse> dthPOJOMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> cumsumerNumber = new MutableLiveData<>();
    public MutableLiveData<String> errorCumsumerNumber = new MutableLiveData<>();


    public void onClick(final View view) {

        if(validate() && !isLoading.getValue()){
            isLoading.setValue(true);
            DTHRechargeRepository.getCustomerInfo(cumsumerNumber.getValue(),operatorMutableLiveData.getValue().type).observeForever(new Observer<DthResponse>() {
                @Override
                public void onChanged(DthResponse dthResponse) {

                    if (dthResponse!=null && dthResponse.getStatus().equalsIgnoreCase("success")) {

                        if (null != dthResponse.getData()) {
                           ((DTHRechargeActivity)view.getContext()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_container, MakeDTHPayment.newInstance(cumsumerNumber.getValue(),operatorMutableLiveData.getValue())).addToBackStack(null)
                                    .commit();
                        } else {
                            dthResponse.setStatus("error");
                        }
                    } else {
                        dthPOJOMutableLiveData.postValue(dthResponse);
                        isLoading.setValue(false);
                    }
                    dthPOJOMutableLiveData.postValue(dthResponse);
                    isLoading.setValue(false);
                }
            });
        } else {

            isLoading.setValue(false);
        }


    }
    public boolean validate() {
        boolean result = true;

        if (cumsumerNumber.getValue() == null || cumsumerNumber.getValue().isEmpty() || cumsumerNumber.getValue().length() < 11) {
            errorCumsumerNumber.setValue("Please Enter Subscription ID");

            result = false;
        }

        return result;
    }

    public void onCumsumerNumberTextChanged(CharSequence s, int start, int before, int count) {
        errorCumsumerNumber.setValue(null);
    }

}
