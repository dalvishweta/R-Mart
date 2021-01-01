package com.rmart.customer.shops.products.repositories;

import com.rmart.BuildConfig;
import com.rmart.customer.shops.home.api.Shops;
import com.rmart.customer.shops.home.model.ShopHomePageResponce;
import com.rmart.customer.shops.products.api.Products;
import com.rmart.customer.shops.products.model.ProductsResponce;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class ProductsRepository {

    public static MutableLiveData<ProductsResponce> getVenderProducts(int vendorId, int shop_id, String categoeryid, String searchPrase,String start_page,String sub_category_id){

        Products shope = RetrofitClientInstance.getRetrofitInstance().create(Products.class);
        final MutableLiveData<ProductsResponce> resultMutableLiveData = new MutableLiveData<>();
        Call<ProductsResponce> call = shope.getVenderProducts(CLIENT_ID,vendorId,shop_id, MyProfile.getInstance().getUserID(),categoeryid,searchPrase,start_page,sub_category_id);
        final ProductsResponce result = new ProductsResponce();

        call.enqueue(new Callback<ProductsResponce>() {
            @Override
            public void onResponse(Call<ProductsResponce> call, Response<ProductsResponce> response) {
                ProductsResponce data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ProductsResponce> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Enternet Connection");

                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                resultMutableLiveData.setValue(result);


            }
        });
        return resultMutableLiveData;

    }


}
