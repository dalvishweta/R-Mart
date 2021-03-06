package com.rmart.customer.order.summary.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rmart.BuildConfig;
import com.rmart.customer.order.summary.api.OrderSummaryApi;
import com.rmart.customer.order.summary.model.OrderedSummaryResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class OrderSummaryRepository {

    public static MutableLiveData<OrderedSummaryResponse> showCartOrderDetails(Context context,int vendorId, int shop_id, String user_address_id, String delivery_method, String coupon_code ,String mode_of_payment){
        OrderSummaryApi orderSummaryApi = RetrofitClientInstance.getRetrofitInstance().create(OrderSummaryApi.class);
        final MutableLiveData<OrderedSummaryResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<OrderedSummaryResponse> call = orderSummaryApi.showCartOrderDetails(CLIENT_ID,vendorId,shop_id,user_address_id, MyProfile.getInstance(context).getUserID(),delivery_method,coupon_code,MyProfile.getInstance(context).getRoleID(),mode_of_payment);
        final OrderedSummaryResponse result = new OrderedSummaryResponse();

        call.enqueue(new Callback<OrderedSummaryResponse>() {
            @Override
            public void onResponse(Call<OrderedSummaryResponse> call, Response<OrderedSummaryResponse> response) {
                OrderedSummaryResponse data = response.body();
                resultMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<OrderedSummaryResponse> call, Throwable t) {
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
