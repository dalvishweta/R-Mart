package com.rmart.customerservice.dth.viewmodels;

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
    public MutableLiveData<String> cumsumerAmount = new MutableLiveData<>();
    public MutableLiveData<String> errorCumsumerAmount = new MutableLiveData<>();

    public void onClick(final View view) {

        if(validate() && !isLoading.getValue()){
            isLoading.setValue(true);
            DTHRechargeRepository.getCustomerInfo(cumsumerNumber.getValue(),operatorMutableLiveData.getValue().mplanOperator).observeForever(new Observer<DthResponse>() {
                @Override
                public void onChanged(DthResponse dthResponse) {
                            if(dthResponse.getStatus()==200) {
                                ((DTHRechargeActivity) view.getContext()).getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frame_container, MakeDTHPayment.newInstance(cumsumerNumber.getValue(), operatorMutableLiveData.getValue(),dthResponse)).addToBackStack(null)
                                        .commit();
                            } else {
                                dthPOJOMutableLiveData.postValue(dthResponse);
                            }
                            isLoading.setValue(false);
                }
            });
        } else {
            isLoading.setValue(false);
        }
    }
    public boolean validate() {
        boolean result = true;
        if (cumsumerNumber.getValue() == null || cumsumerNumber.getValue().isEmpty() || cumsumerNumber.getValue().length() < 10) {
            errorCumsumerNumber.setValue("Please Enter Subscription ID");
            result = false;
        }

        return result;
    }

    public void onCumsumerNumberTextChanged(CharSequence s, int start, int before, int count) {
        errorCumsumerNumber.setValue(null);
    }

    public void onPayClick(final View view) {

        if(validateAmount() && !isLoading.getValue()){
            isLoading.setValue(true);

            //Call GET RSA key
        } else {

            isLoading.setValue(false);
        }


    }

    public boolean validateAmount() {
        boolean result = true;

        if (cumsumerAmount.getValue() == null || cumsumerAmount.getValue().isEmpty() || cumsumerAmount.getValue().length() < 10) {
            errorCumsumerAmount.setValue("Please Enter Valid Amount");

            result = false;
        }

        return result;
    }


}
