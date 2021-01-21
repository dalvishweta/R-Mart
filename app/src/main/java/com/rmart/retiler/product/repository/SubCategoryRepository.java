package com.rmart.retiler.product.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.retiler.product.api.RetailerProductDetailsApi;
import com.rmart.retiler.product.model.product.ProductListRequest;
import com.rmart.retiler.product.model.product.ProductListResponse;
import com.rmart.retiler.product.model.subCategory.SubCategoryListRequest;
import com.rmart.retiler.product.model.subCategory.SubCategoryListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryRepository {
    public static MutableLiveData<SubCategoryListResponse> getAllSubCategories(SubCategoryListRequest request){
        RetailerProductDetailsApi api = RetrofitClientInstance.getRetrofitInstance().create(RetailerProductDetailsApi.class);
        MutableLiveData<SubCategoryListResponse> subCategoryListResponse = new MutableLiveData<>();
        Call<SubCategoryListResponse> call = api.getAllSubCategories(request.getStartIndex(), request.getEndIndex(),
                request.getSubCategoryId());

        call.enqueue(new Callback<SubCategoryListResponse>() {
            @Override
            public void onResponse(Call<SubCategoryListResponse> call, Response<SubCategoryListResponse> response) {
                Log.e("Sub category response", ""+response.body().toString());
                subCategoryListResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SubCategoryListResponse> call, Throwable t) {
                SubCategoryListResponse response = new SubCategoryListResponse();
                response.setStatus("Failed");
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname")) {
                    response.setMsg("Please Check Internet Connection");
                }else response.setMsg(t.getLocalizedMessage());
                subCategoryListResponse.setValue(response);
            }
        });
        return subCategoryListResponse;
    }
}
