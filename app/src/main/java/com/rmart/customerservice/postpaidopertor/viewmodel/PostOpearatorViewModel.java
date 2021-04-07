package com.rmart.customerservice.postpaidopertor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rmart.customerservice.postpaidopertor.model.PostOperatorBaseResponse;
import com.rmart.customerservice.postpaidopertor.repositories.PostOperatorListRepository;

public class PostOpearatorViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

   public MutableLiveData<PostOperatorBaseResponse> PostOperatorListResponseMutableLiveData = new MutableLiveData<>();
    public void getPostOperatorList()
    {
        isLoading.setValue(true);
        MutableLiveData<PostOperatorBaseResponse> resultMutableLiveData= PostOperatorListRepository.getPostOPeratorList();
        resultMutableLiveData.observeForever(brandListResponse -> {
            PostOperatorListResponseMutableLiveData.setValue(brandListResponse);
            isLoading.setValue(false);

        });
    }
}
