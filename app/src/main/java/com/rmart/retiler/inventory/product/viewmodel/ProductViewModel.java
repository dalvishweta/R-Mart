package com.rmart.retiler.inventory.product.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.retiler.inventory.brand.model.BrandListResponse;
import com.rmart.retiler.inventory.brand.repositories.BrandListRepository;
import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.retiler.inventory.product.model.ProductListResponse;
import com.rmart.retiler.inventory.product.repositories.ProductListRepository;

public class ProductViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Category> categoryID = new MutableLiveData<>();
    public MutableLiveData<Brand> brandID = new MutableLiveData<>();
    public MutableLiveData<String> searchPhrase = new MutableLiveData<>();

   public MutableLiveData<ProductListResponse> productListResponseMutableLiveData = new MutableLiveData<>();
    public void getProductList(String page)
    {
        isLoading.setValue(true);
        MutableLiveData<ProductListResponse> resultMutableLiveData= ProductListRepository.getProductList(categoryID.getValue()!=null?categoryID.getValue().getId()+"":null,brandID.getValue()!=null?brandID.getValue().getId()+"":null,searchPhrase.getValue(),page);
        resultMutableLiveData.observeForever(new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                productListResponseMutableLiveData.setValue(productListResponse);
                isLoading.setValue(false);

            }
        });
    }
}
