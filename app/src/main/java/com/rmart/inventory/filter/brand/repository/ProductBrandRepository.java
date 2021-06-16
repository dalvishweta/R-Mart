package com.rmart.inventory.filter.brand.repository;

import com.rmart.inventory.filter.brand.api.BrandApi;
import com.rmart.inventory.filter.brand.models.ProductBrandReponse;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductBrandRepository {
    public static MutableLiveData<ProductBrandReponse> getProductsBransList(String startIndex, String endIndex,String BrandId,String VenderID){

        BrandApi brandListApi = RetrofitClientInstance.getRetrofitInstanceForAddP().create(BrandApi.class);
        final MutableLiveData<ProductBrandReponse> resultMutableLiveData = new MutableLiveData<>();

        Call<ProductBrandReponse> call = brandListApi.getProductBrandInfo(startIndex,endIndex,BrandId,VenderID,"1");

        call.enqueue(new Callback<ProductBrandReponse>() {
            @Override
            public void onResponse(Call<ProductBrandReponse> call, Response<ProductBrandReponse> response) {
                ProductBrandReponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ProductBrandReponse> call, Throwable t) {
                final ProductBrandReponse result = new ProductBrandReponse();
                if(t.getLocalizedMessage().contains("hostname"))
                {
                    result.setMsg("Please Check Internet Connection");

                } else {
                    result.setMsg(t.getMessage());
                }
                result.setStatus("Failure");
                resultMutableLiveData.setValue(result);
            }
        });


        return resultMutableLiveData;

    }


}


