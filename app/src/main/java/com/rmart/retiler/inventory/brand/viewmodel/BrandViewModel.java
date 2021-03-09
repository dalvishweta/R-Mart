package com.rmart.retiler.inventory.brand.viewmodel;

import com.rmart.retiler.inventory.brand.model.BrandListResponse;
import com.rmart.retiler.inventory.brand.repositories.BrandListRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class BrandViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

   public MutableLiveData<BrandListResponse> brandListResponseMutableLiveData = new MutableLiveData<>();
    public void getBransList(String id,String venderID)
    { isLoading.setValue(true);
        MutableLiveData<BrandListResponse> resultMutableLiveData= BrandListRepository.getBransList("0","5000",id,venderID);
        resultMutableLiveData.observeForever(brandListResponse -> {
            brandListResponseMutableLiveData.setValue(brandListResponse);
            isLoading.setValue(false);

        });
    }
}
