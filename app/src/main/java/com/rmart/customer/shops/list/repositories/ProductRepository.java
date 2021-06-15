package com.rmart.customer.shops.list.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customer.shops.list.models.ProductSearchResponce;
import com.rmart.customer.shops.products.api.Products;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class ProductRepository {

    public static MutableLiveData<ProductSearchResponce> searchProduct(Context c,int page, Double latitude, Double longitude, String search_phrase){

        Products products = RetrofitClientInstance.getRetrofitInstance().create(Products.class);
        final MutableLiveData<ProductSearchResponce> resultMutableLiveData = new MutableLiveData<>();
        Call<ProductSearchResponce> call = products.searchProduct(page,CLIENT_ID,latitude,longitude,search_phrase, MyProfile.getInstance(c).getRoleID());
        final ProductSearchResponce result = new ProductSearchResponce();

        call.enqueue(new Callback<ProductSearchResponce>() {
            @Override
            public void onResponse(Call<ProductSearchResponce> call, Response<ProductSearchResponce> response) {
                ProductSearchResponce data = response.body();
                if(data!=null) {
                    resultMutableLiveData.setValue(data);
                }else {
                    result.setMsg("Please Try After Some Time");
                    result.setStatus("faild");
                    resultMutableLiveData.setValue(result);
                }

            }

            @Override
            public void onFailure(Call<ProductSearchResponce> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"hungryindia.co.in\": No address associated with hostname"))
                { result.setMsg("Please Check Internet Connection");

                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                result.setStatus("failed");
                resultMutableLiveData.setValue(result);


            }
        });
        return resultMutableLiveData;

    }
    public static MutableLiveData<ProductSearchResponce> searchShopProduct(Context c,int page, Double latitude, Double longitude, String search_phrase){

        Products products = RetrofitClientInstance.getRetrofitInstanceForCustomer().create(Products.class);
        final MutableLiveData<ProductSearchResponce> resultMutableLiveData = new MutableLiveData<>();
        Call<ProductSearchResponce> call = products.searchShopProduct(page,CLIENT_ID,latitude,longitude,search_phrase, MyProfile.getInstance(c).getRoleID());
        final ProductSearchResponce result = new ProductSearchResponce();

        call.enqueue(new Callback<ProductSearchResponce>() {
            @Override
            public void onResponse(Call<ProductSearchResponce> call, Response<ProductSearchResponce> response) {
                ProductSearchResponce data = response.body();
                if(data!=null) {
                    resultMutableLiveData.setValue(data);
                }else {
                    result.setMsg("Please Try After Some Time");
                    result.setStatus("faild");
                    resultMutableLiveData.setValue(result);
                }

            }

            @Override
            public void onFailure(Call<ProductSearchResponce> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"hungryindia.co.in\": No address associated with hostname"))
                { result.setMsg("Please Check Internet Connection");

                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                result.setStatus("failed");
                resultMutableLiveData.setValue(result);


            }
        });
        return resultMutableLiveData;

    }


}