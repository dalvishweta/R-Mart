package com.rmart.retiler.product.repository;

import androidx.lifecycle.MutableLiveData;

import com.rmart.retiler.product.api.RetailerProductDetailsApi;
import com.rmart.retiler.product.model.brand.BrandListRequest;
import com.rmart.retiler.product.model.brand.BrandListResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandListRepository {
    public static MutableLiveData<BrandListResponse> getBransList(BrandListRequest request){

        RetailerProductDetailsApi brandListApi = RetrofitClientInstance.getRetrofitInstance().create(RetailerProductDetailsApi.class);
        final MutableLiveData<BrandListResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<BrandListResponse> call = brandListApi.getBrandList(request.getStartIndex(), request.getEndIndex(), request.getBrandId(),request.getVenderID());

        call.enqueue(new Callback<BrandListResponse>() {
            @Override
            public void onResponse(Call<BrandListResponse> call, Response<BrandListResponse> response) {
                BrandListResponse data = response.body();
                data.setStatus("Success");
                data.setMsg("Success");
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
