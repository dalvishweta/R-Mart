package com.rmart.electricity.billdetails.modules;

import android.app.ProgressDialog;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.R;
import com.rmart.electricity.RSAKeyResponse;
import com.rmart.electricity.billdetails.repositoris.ProcessRSAKeyRepository;
import com.rmart.electricity.fetchbill.model.BillDetails;
import com.rmart.electricity.selectoperator.model.Operator;
import com.rmart.utilits.Utils;

public class BillDetailsModule extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Operator> operatorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<BillDetails> billDetailsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<RSAKeyResponse> rsakeyResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> mobilenumber = new MutableLiveData<>();
    public MutableLiveData<String> bill_unit = new MutableLiveData<>();

    public void onClick(final View view) {

        if (Utils.isNetworkConnected(view.getContext())) {
            isLoading.setValue(true);
            ProgressDialog progressBar = new ProgressDialog(view.getContext(), R.style.mySpinnerTheme);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            //progressBar.show();
            BillDetails billDetails = billDetailsMutableLiveData.getValue();
           // billDetails.getDueAmount()
            ProcessRSAKeyRepository.getElecticityProcessRsaKey(view.getContext(),billDetails.getBillDate()+"",billDetails.getServiceId()+"",billDetails.getOrderId()+"").observeForever(new Observer<RSAKeyResponse>() {
                @Override
                public void onChanged(RSAKeyResponse rsakeyResponse) {

                    rsakeyResponseMutableLiveData.postValue(rsakeyResponse);

                    isLoading.setValue(false);
                }
            });

        }
    }


}