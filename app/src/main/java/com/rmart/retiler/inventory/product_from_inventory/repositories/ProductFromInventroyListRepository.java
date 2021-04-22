package com.rmart.retiler.inventory.product_from_inventory.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.inventory.models.UpdateReasponce;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductFromInventoryListResponse;
import com.rmart.retiler.inventory.product_from_inventory.api.ProductFromInventoryListSearchApi;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.pojos.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFromInventroyListRepository {

    public static MutableLiveData<ProductFromInventoryListResponse> getProductList(String category_id, String mobile, String brand_id, String search_phrase, String page,String isactive){

        ProductFromInventoryListSearchApi productListSearchApi = RetrofitClientInstance.getRetrofitInstance().create(ProductFromInventoryListSearchApi.class);
        final MutableLiveData<ProductFromInventoryListResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<ProductFromInventoryListResponse> call = productListSearchApi.getProductSearch(category_id,mobile,brand_id,search_phrase,page,isactive);

        call.enqueue(new Callback<ProductFromInventoryListResponse>() {
            @Override
            public void onResponse(Call<ProductFromInventoryListResponse> call, Response<ProductFromInventoryListResponse> response) {
                ProductFromInventoryListResponse data = response.body();
                if(data.getCode()!=null && !data.getCode().equalsIgnoreCase("200")) {
                    if(Integer.parseInt(page)==0) {
                        resultMutableLiveData.setValue(data);
                    }
                } else {
                    resultMutableLiveData.setValue(data);
                }
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
    public static MutableLiveData<UpdateReasponce> isactive(String product_id, String isActive,String client_id ){

        ProductFromInventoryListSearchApi productListSearchApi = RetrofitClientInstance.getRetrofitInstance().create(ProductFromInventoryListSearchApi.class);
        final MutableLiveData<UpdateReasponce> resultMutableLiveData = new MutableLiveData<>();

        Call<UpdateReasponce> call = productListSearchApi.isactive(product_id,isActive,client_id);

        call.enqueue(new Callback<UpdateReasponce>() {
            @Override
            public void onResponse(Call<UpdateReasponce> call, Response<UpdateReasponce> response) {
                UpdateReasponce data = response.body();
                resultMutableLiveData.setValue(data);

            }

            @Override
            public void onFailure(Call<UpdateReasponce> call, Throwable t) {
                final UpdateReasponce result = new UpdateReasponce();
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
