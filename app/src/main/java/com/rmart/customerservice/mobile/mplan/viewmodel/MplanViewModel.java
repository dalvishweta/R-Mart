package com.rmart.customerservice.mobile.mplan.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.customerservice.mobile.mplan.model.MplanBaseResponse;
import com.rmart.customerservice.mobile.mplan.repositories.MplanRepository;

public class MplanViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

   public MutableLiveData<MplanBaseResponse> mplanListResponseMutableLiveData = new MutableLiveData<>();
    public void getBransList(String id,String venderID)
    { isLoading.setValue(true);
        MutableLiveData<MplanBaseResponse> resultMutableLiveData= MplanRepository.getPlanList("Airtel","Maharashtra goa","p","Y","1.0","8446399429");
        resultMutableLiveData.observeForever(mplanResponse -> {
            mplanListResponseMutableLiveData.setValue(mplanResponse);
            isLoading.setValue(false);

        });
    }
}
