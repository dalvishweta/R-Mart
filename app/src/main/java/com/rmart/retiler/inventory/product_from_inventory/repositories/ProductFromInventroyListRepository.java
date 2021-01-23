package com.rmart.retiler.inventory.product_from_inventory.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.product_from_inventory.Model.productFromInventoryListResponse;
import com.rmart.retiler.inventory.product_from_inventory.api.ProductFromInventoryListSearchApi;
import com.rmart.retiler.inventory.product_from_library.model.ProductListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFromInventroyListRepository {

    public static MutableLiveData<productFromInventoryListResponse> getProductList(String category_id,String mobile, String brand_id, String search_phrase,String page){

        ProductFromInventoryListSearchApi productListSearchApi = RetrofitClientInstance.getRetrofitInstance().create(ProductFromInventoryListSearchApi.class);
        final MutableLiveData<productFromInventoryListResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<productFromInventoryListResponse> call = productListSearchApi.getProductSearch(category_id,mobile,brand_id,search_phrase,page);

        call.enqueue(new Callback<productFromInventoryListResponse>() {
            @Override
            public void onResponse(Call<productFromInventoryListResponse> call, Response<productFromInventoryListResponse> response) {
                productFromInventoryListResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<productFromInventoryListResponse> call, Throwable t) {
                final productFromInventoryListResponse result = new productFromInventoryListResponse();
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
