package com.rmart.retiler.product.repository;

import android.util.Log;

import androidx.databinding.library.baseAdapters.BuildConfig;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.rmart.retiler.product.api.RetailerProductDetailsApi;
import com.rmart.retiler.product.model.category.CategoryListRequest;
import com.rmart.retiler.product.model.category.CategoryListResponse;
import com.rmart.retiler.product.model.product.addproduct.AddProductRequest;
import com.rmart.retiler.product.model.product.addproduct.AddProductResponse;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.RetrofitClientInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductRepository {
    public static MutableLiveData<AddProductResponse> addProduct(AddProductRequest request){
        RetailerProductDetailsApi api = RetrofitClientInstance.getRetrofitInstanceForAddProduct()
                .create(RetailerProductDetailsApi.class);
        MutableLiveData<AddProductResponse> addProductResponse = new MutableLiveData<>();
//        String req = request.toString().replaceAll("^\"|\"$&", "");
        JSONObject object = null;
        try {
//            object = request.getJSONObject(request.toString());
            Log.e("AddProductRepository", "object: "+object);
        }catch (Exception e){
            Log.e("AddProductRepository", e.getMessage());
        }
        Call<AddProductResponse> call = api.addProduct(request.getJsonObject());//.toString());//.replaceAll("//", ""));

        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {
                addProductResponse.setValue(response.body());
                Log.d("AddNewProductActivity1", addProductResponse.getValue().getStatus());//response.body());//
            }

            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                AddProductResponse response = new AddProductResponse();
                if (call != null){
                    Log.d( "AddProductResponse cl: " , ""+call.toString());
                }
                response.setStatus("Failed");
                Log.d( "AddProductResponse t: " , t.getMessage());
                if(t.getLocalizedMessage().equalsIgnoreCase("hostname")) {
                    response.setMsg("Please Check Internet Connection");
                }else response.setMsg(t.getLocalizedMessage());
                addProductResponse.setValue(response);
            }
        });
        return addProductResponse;
    }
}
