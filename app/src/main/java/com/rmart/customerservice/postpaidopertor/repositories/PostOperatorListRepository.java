package com.rmart.customerservice.postpaidopertor.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customerservice.postpaidopertor.api.PostPaidOperatorApi;
import com.rmart.customerservice.postpaidopertor.model.PostOperatorBaseResponse;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostOperatorListRepository {

    public static MutableLiveData<PostOperatorBaseResponse> getPostOPeratorList(){

        PostPaidOperatorApi postoperatorListApi = RetrofitClientInstance.getRetrofitInstance().create(PostPaidOperatorApi.class);
        final MutableLiveData<PostOperatorBaseResponse> resultMutableLiveData = new MutableLiveData<>();

        Call<PostOperatorBaseResponse> call = postoperatorListApi.getPostOPeratorList();

        call.enqueue(new Callback<PostOperatorBaseResponse>() {
            @Override
            public void onResponse(Call<PostOperatorBaseResponse> call, Response<PostOperatorBaseResponse> response) {
                PostOperatorBaseResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<PostOperatorBaseResponse> call, Throwable t) {
                final PostOperatorBaseResponse result = new PostOperatorBaseResponse();
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
