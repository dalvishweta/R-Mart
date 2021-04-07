package com.rmart.customer.shops.products.viewmodel;

import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.shops.products.model.ProductsResponce;
import com.rmart.customer.shops.products.repositories.ProductsRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductListViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<ProductsResponce> shopHomePageResponceMutableLiveData = new MutableLiveData<>();


    public void loadProductList(ShopDetailsModel productsShopDetailsModel, String categoeryid, String searchPrase, String start_page, String sub_category_id,String productstype){
        isLoading.setValue(true);
        ProductsRepository.getVenderProducts(productsShopDetailsModel.getVendorId(),productsShopDetailsModel.getShopId(), categoeryid, searchPrase,start_page, sub_category_id,productstype).observeForever(productResult -> {
            shopHomePageResponceMutableLiveData.setValue(productResult);
            isLoading.postValue(false);
            try {
                if (shopHomePageResponceMutableLiveData.getValue().results.productData.size() == 0) {
                    ProductsResponce productsResponceMutableLiveData= shopHomePageResponceMutableLiveData.getValue();
                    productsResponceMutableLiveData.setStatus(300);
                    productsResponceMutableLiveData.setMsg("Product Not Available");
                    shopHomePageResponceMutableLiveData.setValue(productsResponceMutableLiveData);

                }
            }catch (Exception e){

            }
        });




    }


}
