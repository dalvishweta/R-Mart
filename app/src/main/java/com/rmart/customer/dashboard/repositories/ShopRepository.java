package com.rmart.customer.dashboard.repositories;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customer.dashboard.api.Shops;
import com.rmart.customer.dashboard.model.HomePageData;
import com.rmart.customer.dashboard.model.HomePageResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class ShopRepository {

    public static MutableLiveData<HomePageResponse> getShopHomePageNEW(){

        Shops shope = RetrofitClientInstance.getRetrofitInstance().create(Shops.class);
        final MutableLiveData<HomePageResponse> resultMutableLiveData = new MutableLiveData<>();
        String type = MyProfile.getInstance().getRoleID();
        Call<HomePageResponse> call = shope.getShopHomePageNEW(CLIENT_ID);
        final HomePageResponse result = new HomePageResponse();

        call.enqueue(new Callback<HomePageResponse>() {
            @Override
            public void onResponse(Call<HomePageResponse> call, Response<HomePageResponse> response) {
                HomePageResponse data = response.body();
                if(data!=null) {
                    resultMutableLiveData.setValue(data);

                } else {
                    result.setMsg(data.getMsg());
                    result.setStatus("400");
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<HomePageResponse> call, Throwable t) {
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
