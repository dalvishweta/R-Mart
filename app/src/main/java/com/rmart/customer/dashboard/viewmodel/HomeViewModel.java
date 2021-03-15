package com.rmart.customer.dashboard.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.rmart.customer.dashboard.model.HomePageResponse;
import com.rmart.customer.dashboard.repositories.ShopRepository;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<HomePageResponse> shopHomePageResponceMutableLiveData = new MutableLiveData<>();


    public void loadShopHomePage(){
        isLoading.setValue(true);
        ShopRepository.getShopHomePageNEW().observeForever(homeResult -> {
            shopHomePageResponceMutableLiveData.setValue(homeResult);
            isLoading.postValue(false);
        });




    }

}
