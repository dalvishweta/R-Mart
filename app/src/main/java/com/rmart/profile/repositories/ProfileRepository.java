package com.rmart.profile.repositories;

import com.rmart.BuildConfig;
import com.rmart.customer.shops.products.api.Products;
import com.rmart.customer.shops.products.model.ProductsResponce;
import com.rmart.profile.api.Profile;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.model.ShopTypeResponce;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class ProfileRepository {

    public static MutableLiveData<ShopTypeResponce> getVenderProducts(){

        Profile profile = RetrofitClientInstance.getRetrofitInstance().create(Profile.class);
        final MutableLiveData<ShopTypeResponce> resultMutableLiveData = new MutableLiveData<>();
        Call<ShopTypeResponce> call = profile.getShopTypes(CLIENT_ID);
        final ShopTypeResponce result = new ShopTypeResponce();

        call.enqueue(new Callback<ShopTypeResponce>() {
            @Override
            public void onResponse(Call<ShopTypeResponce> call, Response<ShopTypeResponce> response) {
                ShopTypeResponce data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<ShopTypeResponce> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \""+ BuildConfig.BASE_URL+"\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Enternet Connection");

                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                resultMutableLiveData.setValue(result);


            }
        });
        return resultMutableLiveData;

    }


}
