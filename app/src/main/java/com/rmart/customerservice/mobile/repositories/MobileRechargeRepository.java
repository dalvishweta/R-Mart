package com.rmart.customerservice.mobile.repositories;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rmart.R;
import com.rmart.customer.shops.home.api.Shops;
import com.rmart.customer.shops.home.model.ShopHomePageResponce;
import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.models.LastTransaction;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.models.SubscriberModule;
import com.rmart.customerservice.mobile.views.MobileRechargeHistoryFragment;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import java.net.SocketTimeoutException;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class MobileRechargeRepository {

    public static MutableLiveData<ResponseGetHistory> getHistory() {

        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstance().create(MobileRechargeService.class);
        final MutableLiveData<ResponseGetHistory> resultMutableLiveData = new MutableLiveData<>();
        String type = MyProfile.getInstance().getRoleID();
        Call<ResponseGetHistory> call = mobileRechargeService.getHistory(MyProfile.getInstance().getUserID());
        final ResponseGetHistory result = new ResponseGetHistory();

        call.enqueue(new Callback<ResponseGetHistory>() {
            @Override
            public void onResponse(Call<ResponseGetHistory> call, Response<ResponseGetHistory> response) {
                ResponseGetHistory data = response.body();
                if(data!=null && data.getLastTransaction()!=null) {
                    resultMutableLiveData.setValue(data);
                 } else {
                    
                    result.setStatus("failed");
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetHistory> call, Throwable t) {
                if(t.getLocalizedMessage().equalsIgnoreCase("Unable to resolve host \"hungryindia.co.in\": No address associated with hostname"))
                {
                    result.setMsg("Please Check Internet Connection");
                } else {
                    result.setMsg(t.getLocalizedMessage());
                }
                resultMutableLiveData.setValue(result);
           }
        });
        return resultMutableLiveData;
    }
}
