package com.rmart.retiler.inventory.product_from_inventory.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductFromInventoryListResponse;
import com.rmart.retiler.inventory.product_from_inventory.api.ProductFromInventoryListSearchApi;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFromInventroyListRepository {

    public static MutableLiveData<ProductFromInventoryListResponse> getProductList(String category_id, String mobile, String brand_id, String search_phrase, String page){

        ProductFromInventoryListSearchApi productListSearchApi = RetrofitClientInstance.getRetrofitInstance().create(ProductFromInventoryListSearchApi.class);
        final MutableLiveData<ProductFromInventoryListResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<ProductFromInventoryListResponse> call = productListSearchApi.getProductSearch(category_id,mobile,brand_id,search_phrase,page);

        call.enqueue(new Callback<ProductFromInventoryListResponse>() {
            @Override
            public void onResponse(Call<ProductFromInventoryListResponse> call, Response<ProductFromInventoryListResponse> response) {
                ProductFromInventoryListResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ProductFromInventoryListResponse> call, Throwable t) {
                final ProductFromInventoryListResponse result = new ProductFromInventoryListResponse();
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
