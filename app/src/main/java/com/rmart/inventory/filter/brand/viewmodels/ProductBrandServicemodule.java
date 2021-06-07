package com.rmart.inventory.filter.brand.viewmodels;

import com.rmart.inventory.filter.brand.models.ProductBrandReponse;
import com.rmart.inventory.filter.brand.repository.ProductBrandRepository;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductBrandServicemodule extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<ProductBrandReponse> brandListResponseMutableLiveData = new MutableLiveData<>();
    public void getBransList(String id,String venderID)
    { isLoading.setValue(true);
        MutableLiveData<ProductBrandReponse> resultMutableLiveData= ProductBrandRepository.getProductsBransList("0","100",id,venderID);
        resultMutableLiveData.observeForever(brandListResponse -> {
            brandListResponseMutableLiveData.setValue(brandListResponse);
            isLoading.setValue(false);

        });
    }
}

