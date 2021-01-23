package com.rmart.retiler.product.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rmart.R;
import com.rmart.retiler.product.api.RetailerProductDetailsApi;
import com.rmart.retiler.product.model.product.addproduct.AddProductResponse;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.APIUnitMeasureListResponse;
import com.rmart.utilits.pojos.APIUnitMeasureResponse;
import com.rmart.utilits.services.APIService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnitDetailsRepository {
    public static MutableLiveData<APIStockListResponse> getAPIStockList(){
        RetailerProductDetailsApi api = RetrofitClientInstance.getRetrofitInstance().create(RetailerProductDetailsApi.class);
        MutableLiveData<APIStockListResponse> stockListResponse = new MutableLiveData<>();
        Call<APIStockListResponse> call = api.getAPIStockList();

        call.enqueue(new Callback<APIStockListResponse>() {
            @Override
            public void onResponse(Call<APIStockListResponse> call, Response<APIStockListResponse> response) {
                stockListResponse.setValue(response.body());
                Log.d("AddNewProductActivity1", stockListResponse.getValue().getStatus());
            }

            @Override
            public void onFailure(Call<APIStockListResponse> call, Throwable t) {
                APIStockListResponse response = new APIStockListResponse();
                response.setStatus("Failed");
                if(t.getLocalizedMessage().equalsIgnoreCase("hostname")) {
                    response.setMsg("Please Check Internet Connection");
                }else response.setMsg(t.getLocalizedMessage());
                stockListResponse.setValue(response);
            }
        });
        return stockListResponse;
    }

    public static MutableLiveData<APIUnitMeasureListResponse> getUnitMeasureList(){
        RetailerProductDetailsApi api = RetrofitClientInstance.getRetrofitInstance().create(RetailerProductDetailsApi.class);
        MutableLiveData<APIUnitMeasureListResponse> unitMeasureResponse = new MutableLiveData<>();
        Call<APIUnitMeasureListResponse> call = api.getAPIUnitMeasureList();

        call.enqueue(new Callback<APIUnitMeasureListResponse>() {
            @Override
            public void onResponse(Call<APIUnitMeasureListResponse> call, Response<APIUnitMeasureListResponse> response) {
                unitMeasureResponse.setValue(response.body());
                Log.d("AddNewProductActivity1", unitMeasureResponse.getValue().getStatus());
            }

            @Override
            public void onFailure(Call<APIUnitMeasureListResponse> call, Throwable t) {
                APIUnitMeasureListResponse response = new APIUnitMeasureListResponse();
                response.setStatus("Failed");
                if(t.getLocalizedMessage().equalsIgnoreCase("hostname")) {
                    response.setMsg("Please Check Internet Connection");
                }else response.setMsg(t.getLocalizedMessage());
                unitMeasureResponse.setValue(response);
            }
        });
        return unitMeasureResponse;
    }
}
