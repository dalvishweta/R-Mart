package com.rmart.retiler.product.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.profile.model.MyProfile;
import com.rmart.retiler.product.api.RetailerProductDetailsApi;
import com.rmart.retiler.product.model.category.CategoryListRequest;
import com.rmart.retiler.product.model.category.CategoryListResponse;
import com.rmart.retiler.product.model.product.ProductListRequest;
import com.rmart.retiler.product.model.product.ProductListResponse;
import com.rmart.retiler.product.model.subCategory.SubCategoryListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    public static MutableLiveData<ProductListResponse> getAllProducts(Context context,ProductListRequest request){
        RetailerProductDetailsApi api = RetrofitClientInstance.getRetrofitInstance().create(RetailerProductDetailsApi.class);
        MutableLiveData<ProductListResponse> productListResponse = new MutableLiveData<>();
        Call<ProductListResponse> call = api.getAllProducts(request.getStartIndex(), request.getEndIndex(),
                request.getMobile(), request.getStockType(), MyProfile.getInstance(context).getUserID());

        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                productListResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                ProductListResponse response = new ProductListResponse();
                response.setStatus("Failed");
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname")) {
                    response.setMsg("Please Check Internet Connection");
                }else response.setMsg(t.getLocalizedMessage());
                productListResponse.setValue(response);
            }
        });
        return productListResponse;
    }
}
