package com.rmart.electricity.billdetails.modules;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.R;
import com.rmart.electricity.PaymentActivity;
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
            ProcessRSAKeyRepository.getElecticityProcessRsaKey(view.getContext(),billDetails.getDueAmount()+"",billDetails.getServiceId()+"",billDetails.getOrderId()+"").observeForever(new Observer<RSAKeyResponse>() {
                @Override
                public void onChanged(RSAKeyResponse rsakeyResponse) {


                    if (rsakeyResponse.getStatus().equalsIgnoreCase("success")) {
                        Intent ii= new Intent(view.getContext(), PaymentActivity.class);
                        ii.putExtra("rsakeyresonse",  rsakeyResponse.getData());
                        ii.putExtra("cust_details",billDetailsMutableLiveData.getValue());
                        ii.putExtra("mobile_number",mobilenumber.getValue());
                        ii.putExtra("bill_unit",bill_unit.getValue());
                        ii.putExtra("operator",operatorMutableLiveData.getValue().slug);
                        view.getContext().startActivity(ii);


                    } else {

                    }

                    rsakeyResponseMutableLiveData.postValue(rsakeyResponse);

                    isLoading.setValue(false);
                }
            });

        }
    }
}