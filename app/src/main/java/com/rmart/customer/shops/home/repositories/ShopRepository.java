package com.rmart.customer.shops.home.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rmart.customer.shops.home.api.Shops;
import com.rmart.customer.shops.home.model.ShopHomePageResponce;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class ShopRepository {

    public static MutableLiveData<ShopHomePageResponce> getShopHomePage(Context c, int vendorId, int shop_id){

        Shops shope = RetrofitClientInstance.getRetrofitInstance().create(Shops.class);
        final MutableLiveData<ShopHomePageResponce> resultMutableLiveData = new MutableLiveData<>();
        String type = MyProfile.getInstance(c).getRoleID();
        Call<ShopHomePageResponce> call = shope.getShopHomePage(CLIENT_ID,vendorId,shop_id, MyProfile.getInstance(c)!=null?MyProfile.getInstance(c).getUserID():"",type);
        final ShopHomePageResponce result = new ShopHomePageResponce();

        call.enqueue(new Callback<ShopHomePageResponce>() {
            @Override
            public void onResponse(Call<ShopHomePageResponce> call, Response<ShopHomePageResponce> response) {
                ShopHomePageResponce data = response.body();
                if(response.isSuccessful()) {
                    if (data.results != null) {
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
            public void onFailure(Call<ShopHomePageResponce> call, Throwable t) {
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
