package com.rmart.retiler.inventory.product.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.retiler.inventory.brand.model.BrandListResponse;
import com.rmart.retiler.inventory.brand.repositories.BrandListRepository;
import com.rmart.retiler.inventory.product.model.ProductListResponse;
import com.rmart.retiler.inventory.product.repositories.ProductListRepository;

public class ProductViewModel extends ViewModel {

   public MutableLiveData<ProductListResponse> productListResponseMutableLiveData = new MutableLiveData<>();
    public void getProductList(String categoryID,String brandId,String search_phase,String page)
    {
        MutableLiveData<ProductListResponse> resultMutableLiveData= ProductListRepository.getProductList(categoryID,brandId,search_phase,page);
        resultMutableLiveData.observeForever(new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                productListResponseMutableLiveData.setValue(productListResponse);
            }
        });
    }
}
