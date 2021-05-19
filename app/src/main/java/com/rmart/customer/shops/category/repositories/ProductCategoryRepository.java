package com.rmart.customer.shops.category.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customer.shops.category.api.Category;
import com.rmart.customer.shops.category.model.CategoryBaseResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class ProductCategoryRepository {

    public static MutableLiveData<CategoryBaseResponse> getProductCategory(int client_id, int vendorId, String cat_ids,int parent_cat_ids){

        Category cat = RetrofitClientInstance.getRetrofitInstance().create(Category.class);
        final MutableLiveData<CategoryBaseResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<CategoryBaseResponse> call = cat.getProductCategory(CLIENT_ID,vendorId,cat_ids, parent_cat_ids);
        final CategoryBaseResponse result = new CategoryBaseResponse();

        call.enqueue(new Callback<CategoryBaseResponse>() {
            @Override
            public void onResponse(Call<CategoryBaseResponse> call, Response<CategoryBaseResponse> response) {
                CategoryBaseResponse data = response.body();

                if(response.isSuccessful()) {
                    if (data.getResult() != null) {
                        resultMutableLiveData.setValue(data);

                    } else {
                        result.setMsg(data.getMsg());
                        result.setStatus(400);
                        resultMutableLiveData.setValue(result);
                    }
                }else {
                    result.setMsg(response.message());
                    result.setStatus(response.code());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<CategoryBaseResponse> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"hungryindia.co.in\": No address associated with hostname"))
                { result.setMsg("Please Check Enternet Connection");

                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                resultMutableLiveData.setValue(result);


            }
        });
        return resultMutableLiveData;

    }


}
