package com.rmart.electricity.fetchbill.module;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.rmart.R;
import com.rmart.electricity.activities.ElectricityActivity;
import com.rmart.electricity.billdetails.fragments.BillDetailsFragment;
import com.rmart.electricity.fetchbill.fragments.FetchBillFragment;
import com.rmart.electricity.fetchbill.model.ElecProcessPOJO;
import com.rmart.electricity.FetchBill;
import com.rmart.electricity.fetchbill.repositoris.FetchBillRepository;
import com.rmart.electricity.selectoperator.model.Operator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class FetchElectricityBillViewModule extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Operator> operatorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ElecProcessPOJO> elecProcessPOJOMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> cumsumerNumber = new MutableLiveData<>();
    public MutableLiveData<String> errorCumsumerNumber = new MutableLiveData<>();
    public MutableLiveData<String> units = new MutableLiveData<>();
    public MutableLiveData<String> errorUnits = new MutableLiveData<>();
    public MutableLiveData<String> mobilenumber = new MutableLiveData<>();
    public MutableLiveData<String> errorMobilenumber = new MutableLiveData<>();



    public void onClick(final View view) {

        if(validate() && !isLoading.getValue()){
            isLoading.setValue(true);
            FetchBillRepository.getBillDetails(view.getContext(),operatorMutableLiveData.getValue().slug,cumsumerNumber.getValue(),units.getValue(),mobilenumber.getValue()).observeForever(new Observer<ElecProcessPOJO>() {
                @Override
                public void onChanged(ElecProcessPOJO elecProcessPOJO) {

                    if (elecProcessPOJO!=null && elecProcessPOJO.getStatus().equalsIgnoreCase("success")) {

                        if (null != elecProcessPOJO.getData().getOrderId()) {
                           ((ElectricityActivity)view.getContext()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_container, BillDetailsFragment.newInstance(mobilenumber.getValue(), units.getValue(),elecProcessPOJO.getData(),operatorMutableLiveData.getValue())).addToBackStack(null)
                                    .commit();
                        } else {
                            elecProcessPOJO.setStatus("error");
                        }
                    } else {


                    }
                    elecProcessPOJOMutableLiveData.postValue(elecProcessPOJO);
                    isLoading.setValue(false);
                }
            });
        } else {

        }


    }
    public boolean validate() {
        boolean result = true;

        if (cumsumerNumber.getValue() == null || cumsumerNumber.getValue().isEmpty()) {
            errorCumsumerNumber.setValue("Please Enter Consumer number");

            result = false;
        }
        if (mobilenumber.getValue() == null || mobilenumber.getValue().isEmpty() || mobilenumber.getValue().length() < 10) {
            errorMobilenumber.setValue("Please Enter mobile number");

            result = false;
        }
        if (operatorMutableLiveData.getValue().slug.equalsIgnoreCase("MSE")) {


            if (units.getValue() == null || units.getValue().isEmpty()) {
                errorUnits.setValue("Please Enter bill unit");
                result = false;
            }


        }
        return result;
    }
    public void onCumsumerNumberTextChanged(CharSequence s, int start, int before, int count) {
        errorCumsumerNumber.setValue(null);
    }
    public void onMobilenumberTextChanged(CharSequence s, int start, int before, int count) {
        errorMobilenumber.setValue(null);
    }
    public void onUnitsTextChanged(CharSequence s, int start, int before, int count) {
        errorUnits.setValue(null);
    }

}
