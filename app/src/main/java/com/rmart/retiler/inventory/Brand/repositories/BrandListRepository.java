package com.rmart.retiler.inventory.Brand.repositories;

import com.rmart.BuildConfig;

import com.rmart.retiler.inventory.Brand.api.BrandListApi;
import com.rmart.retiler.inventory.Brand.model.BrandListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandListRepository {

    public static MutableLiveData<BrandListResponse> getBransList(String startIndex, String endIndex, String categoryID){

        BrandListApi brandListApi = RetrofitClientInstance.getRetrofitInstance().create(BrandListApi.class);
        final MutableLiveData<BrandListResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<BrandListResponse> call = brandListApi.getBrandList(startIndex,endIndex,categoryID);

        call.enqueue(new Callback<BrandListResponse>() {
            @Override
            public void onResponse(Call<BrandListResponse> call, Response<BrandListResponse> response) {
                BrandListResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<BrandListResponse> call, Throwable t) {
                final BrandListResponse result = new BrandListResponse();
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
