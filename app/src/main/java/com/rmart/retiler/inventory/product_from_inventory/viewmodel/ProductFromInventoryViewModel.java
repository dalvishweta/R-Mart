package com.rmart.retiler.inventory.product_from_inventory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.profile.model.MyProfile;
import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductFromInventoryListResponse;
import com.rmart.retiler.inventory.product_from_inventory.repositories.ProductFromInventroyListRepository;


public class ProductFromInventoryViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Category> categoryID = new MutableLiveData<>();
    public MutableLiveData<Brand> brandID = new MutableLiveData<>();
    public MutableLiveData<String> searchPhrase = new MutableLiveData<>();

   public MutableLiveData<ProductFromInventoryListResponse> productListResponseMutableLiveData = new MutableLiveData<>();
    public void getProductList(String page)
    {
        isLoading.setValue(true);
        MutableLiveData<ProductFromInventoryListResponse> resultMutableLiveData= ProductFromInventroyListRepository.getProductList(categoryID.getValue()!=null?categoryID.getValue().getId()+"":null, MyProfile.getInstance()!=null?MyProfile.getInstance().getMobileNumber():null,brandID.getValue()!=null?brandID.getValue().getId()+"":null,searchPhrase.getValue(),page);
        resultMutableLiveData.observeForever(new Observer<ProductFromInventoryListResponse>() {
            @Override
            public void onChanged(ProductFromInventoryListResponse productListResponse) {
                productListResponseMutableLiveData.setValue(productListResponse);
                isLoading.setValue(false);

            }
        });
    }
}
