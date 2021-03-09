package com.rmart.retiler.inventory.category.viewmodels;

import com.rmart.retiler.inventory.brand.model.BrandListResponse;
import com.rmart.retiler.inventory.brand.repositories.BrandListRepository;
import com.rmart.retiler.inventory.category.model.CategoryListResponce;
import com.rmart.retiler.inventory.category.repositories.CategoryListRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

   public MutableLiveData<CategoryListResponce> categoryListResponceMutableLiveData = new MutableLiveData<>();
    public void getCategoryList(String id,String vendor_id)
    {   isLoading.setValue(true);
        CategoryListRepository.getVenderProducts("0", "5000",id, vendor_id).observeForever( brandListResponse -> {
                    categoryListResponceMutableLiveData.setValue(brandListResponse);
                     isLoading.setValue(false);

                }

        );
    }
}
