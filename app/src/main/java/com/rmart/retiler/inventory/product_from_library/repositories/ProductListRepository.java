package com.rmart.retiler.inventory.product_from_library.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.product_from_library.api.ProductListSearchApi;
import com.rmart.retiler.inventory.product_from_library.model.ProductListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListRepository {

    public static MutableLiveData<ProductListResponse> getProductList(String category_id, String brand_id, String search_phrase,String page){

        ProductListSearchApi productListSearchApi = RetrofitClientInstance.getRetrofitInstance().create(ProductListSearchApi.class);
        final MutableLiveData<ProductListResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<ProductListResponse> call = productListSearchApi.getProductSearch(category_id,brand_id,search_phrase,page);

        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                ProductListResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                final ProductListResponse result = new ProductListResponse();
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname"))
                {
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
