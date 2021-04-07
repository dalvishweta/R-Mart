package com.rmart.customerservice.prepaidopertor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.customerservice.prepaidopertor.model.PreOperatorBaseResponse;
import com.rmart.customerservice.prepaidopertor.repositories.PreOperatorListRepository;

public class PreOpearatorViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

   public MutableLiveData<PreOperatorBaseResponse> PreOperatorListResponseMutableLiveData = new MutableLiveData<>();
    public void getPreOperatorList()
    { isLoading.setValue(true);
        MutableLiveData<PreOperatorBaseResponse> resultMutableLiveData= PreOperatorListRepository.getPreOPeratorList();
        resultMutableLiveData.observeForever(brandListResponse -> {
            PreOperatorListResponseMutableLiveData.setValue(brandListResponse);
            isLoading.setValue(false);

        });
    }
}
