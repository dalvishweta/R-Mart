package com.rmart.retiler.inventory.product_from_inventory.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.profile.model.MyProfile;
import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductFromInventoryListResponse;
import com.rmart.retiler.inventory.product_from_inventory.repositories.ProductFromInventroyListRepository;
import com.rmart.utilits.pojos.BaseResponse;


public class ProductFromInventoryViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Category> categoryID = new MutableLiveData<>();
    public MutableLiveData<Brand> brandID = new MutableLiveData<>();
    public MutableLiveData<String> searchPhrase = new MutableLiveData<>();

   public MutableLiveData<ProductFromInventoryListResponse> productListResponseMutableLiveData = new MutableLiveData<>();
    public void getProductList(String page, String isactive, Context context)
    {
        if(Integer.parseInt(page)==0 || (searchPhrase.getValue()!=null && searchPhrase.getValue().length()>0)) {
            isLoading.setValue(true);
        }
        MutableLiveData<ProductFromInventoryListResponse> resultMutableLiveData= ProductFromInventroyListRepository.getProductList(categoryID.getValue()!=null?categoryID.getValue().getId()+"":null, MyProfile.getInstance(context)!=null?MyProfile.getInstance(context).getMobileNumber():null,brandID.getValue()!=null?brandID.getValue().getId()+"":null,searchPhrase.getValue(),page,isactive);
        resultMutableLiveData.observeForever(productListResponse -> {

            isLoading.setValue(false);
            if(productListResponse.getCode()!=null && !productListResponse.getCode().equalsIgnoreCase("200")) {
                if(Integer.parseInt(page)==0) {
                    productListResponseMutableLiveData.setValue(productListResponse);
                }
            } else {
                productListResponseMutableLiveData.setValue(productListResponse);
            }
        });
    }



}
