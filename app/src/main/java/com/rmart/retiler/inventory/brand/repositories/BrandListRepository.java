package com.rmart.retiler.inventory.brand.repositories;

import com.rmart.BuildConfig;

import com.rmart.retiler.inventory.brand.api.BrandListApi;
import com.rmart.retiler.inventory.brand.model.BrandListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandListRepository {

    public static MutableLiveData<BrandListResponse> getBransList(String startIndex, String endIndex, String categoryID, String venderID){

        BrandListApi brandListApi = RetrofitClientInstance.getRetrofitInstance().create(BrandListApi.class);
        final MutableLiveData<BrandListResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<BrandListResponse> call = brandListApi.getBrandList(startIndex,endIndex,categoryID,venderID);

        call.enqueue(new Callback<BrandListResponse>() {
            @Override
            public void onResponse(Call<BrandListResponse> call, Response<BrandListResponse> response) {
                BrandListResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<BrandListResponse> call, Throwable t) {
                final BrandListResponse result = new BrandListResponse();
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
