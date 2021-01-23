package com.rmart.retiler.product.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.retiler.product.api.RetailerProductDetailsApi;
import com.rmart.retiler.product.model.category.CategoryListRequest;
import com.rmart.retiler.product.model.category.CategoryListResponse;
import com.rmart.retiler.product.model.subCategory.SubCategoryListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    public static MutableLiveData<CategoryListResponse> getAllCategories(CategoryListRequest request){
        RetailerProductDetailsApi api = RetrofitClientInstance.getRetrofitInstance().create(RetailerProductDetailsApi.class);
        MutableLiveData<CategoryListResponse> categoryListResponse = new MutableLiveData<>();
        Call<CategoryListResponse> call = api.getAllCategories(request.getStartIndex(), request.getEndIndex(), request.getCategoryId());

        call.enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                categoryListResponse.setValue(response.body());
                Log.d("AddNewProductActivity1", categoryListResponse.getValue().getStatus());
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                CategoryListResponse response = new CategoryListResponse();
                response.setStatus("Failed");
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname")) {
                    response.setMsg("Please Check Internet Connection");
                }else response.setMsg(t.getLocalizedMessage());
                categoryListResponse.setValue(response);
            }
        });
        return categoryListResponse;
    }
}