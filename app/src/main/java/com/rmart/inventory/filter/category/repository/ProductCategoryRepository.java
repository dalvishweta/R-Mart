package com.rmart.inventory.filter.category.repository;

import com.rmart.BuildConfig;
import com.rmart.inventory.filter.brand.api.BrandApi;
import com.rmart.inventory.filter.category.api.CategoryApi;
import com.rmart.inventory.filter.category.models.ProductCategoryResponse;

import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryRepository {
    public static MutableLiveData<ProductCategoryResponse> getProductsCategory(String startIndex, String endIndex,String CategoryId,String VenderId) {

        CategoryApi categoryListApi = RetrofitClientInstance.getRetrofitInstanceForAddP().create(CategoryApi.class);
        final MutableLiveData<ProductCategoryResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<ProductCategoryResponse> call = categoryListApi.getProductCategoryInfo(startIndex, endIndex,CategoryId,VenderId,"1");

        call.enqueue(new Callback<ProductCategoryResponse>() {
            @Override
            public void onResponse(Call<ProductCategoryResponse> call, Response<ProductCategoryResponse> response) {
                ProductCategoryResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ProductCategoryResponse> call, Throwable t) {
                final ProductCategoryResponse result = new ProductCategoryResponse();
                if (t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"" + BuildConfig.BASE_URL + "\": No address associated with hostname")) {
                    result.setMsg("Please Check Enternet Connection");

                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                result.setStatus("Failure");
                resultMutableLiveData.setValue(result);
            }
        });


        return resultMutableLiveData;

    }
}
