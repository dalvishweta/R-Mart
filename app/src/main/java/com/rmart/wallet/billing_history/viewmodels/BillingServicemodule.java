package com.rmart.wallet.billing_history.viewmodels;

import android.util.Log;
import android.view.View;

import com.rmart.profile.model.MyProfile;
import com.rmart.wallet.billing_history.models.BillingResponse;
import com.rmart.wallet.billing_history.models.Datum;
import com.rmart.wallet.billing_history.repository.BillingRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class BillingServicemodule extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> from_date = new MutableLiveData<>();
    public MutableLiveData<String> to_date = new MutableLiveData<>();
    public MutableLiveData<BillingResponse> billingPOJOMutableLiveData = new MutableLiveData<BillingResponse>();
    public MutableLiveData<Datum> datumMutableLiveData = new MutableLiveData<>();

    public void onClick(final View view) {

        if(!isLoading.getValue()){
            isLoading.setValue(true);
            String s= from_date.getValue() + "00:00:00";
            from_date.setValue(s);
            String s1= to_date.getValue() + "00:00:00";
            to_date.setValue(s1);
            Log.d("FROM_DATe",from_date.getValue());
            BillingRepository.getBillingHistory(from_date.getValue(),to_date.getValue(), "70").observeForever(new Observer<BillingResponse>() {
                @Override
                public void onChanged(BillingResponse billingResponse) {
                    if (billingResponse.getStatus()==200){
                        Log.d("billingResponse",billingResponse.getMsg());
                        billingPOJOMutableLiveData.postValue(billingResponse);
                    }
                    else {

                    }
                }
            });

        } else {
            isLoading.setValue(false);
        }
    }

}
