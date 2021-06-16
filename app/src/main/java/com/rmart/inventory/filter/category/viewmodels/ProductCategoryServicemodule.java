package com.rmart.inventory.filter.category.viewmodels;

import android.util.Log;

import com.rmart.inventory.filter.category.models.ProductCategoryResponse;
import com.rmart.inventory.filter.category.repository.ProductCategoryRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductCategoryServicemodule extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<ProductCategoryResponse> categoryListResponceMutableLiveData = new MutableLiveData<>();
    public void getCategoryList(String id,String vendor_id)
    {   isLoading.setValue(true);

        ProductCategoryRepository.getProductsCategory("0", "100",id,vendor_id).observeForever(brandListResponse -> {
                    categoryListResponceMutableLiveData.setValue(brandListResponse);
                    isLoading.setValue(false);

                }

        );
    }
}
