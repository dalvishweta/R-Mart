package com.rmart.retiler.inventory.category.repositories;

import com.rmart.BuildConfig;
import com.rmart.retiler.inventory.category.api.CategoryListApi;
import com.rmart.retiler.inventory.category.model.CategoryListResponce;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListRepository {

    public static MutableLiveData<CategoryListResponce> getVenderProducts(String startIndex, String endIndex, String categoryID){

        CategoryListApi categoryListApi = RetrofitClientInstance.getRetrofitInstance().create(CategoryListApi.class);
        final MutableLiveData<CategoryListResponce> resultMutableLiveData = new MutableLiveData<>();
        Call<CategoryListResponce> call = categoryListApi.getCategoryList(startIndex,endIndex,categoryID);

        call.enqueue(new Callback<CategoryListResponce>() {
            @Override
            public void onResponse(Call<CategoryListResponce> call, Response<CategoryListResponce> response) {
                CategoryListResponce data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<CategoryListResponce> call, Throwable t) {
                final CategoryListResponce result = new CategoryListResponce();
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
