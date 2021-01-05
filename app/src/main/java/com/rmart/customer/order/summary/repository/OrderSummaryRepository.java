package com.rmart.customer.order.summary.repository;

import com.rmart.BuildConfig;
import com.rmart.customer.order.summary.api.OrderSummaryApi;
import com.rmart.customer.order.summary.model.OrderedSummaryResponse;
import com.rmart.customer.shops.products.api.Products;
import com.rmart.customer.shops.products.model.ProductsResponce;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static com.rmart.utilits.Utils.CLIENT_ID;

public class OrderSummaryRepository {

    public static MutableLiveData<OrderedSummaryResponse> showCartOrderDetails(int vendorId, int shop_id, String user_address_id, String delivery_method,String coupon_code){
        OrderSummaryApi orderSummaryApi = RetrofitClientInstance.getRetrofitInstance().create(OrderSummaryApi.class);
        final MutableLiveData<OrderedSummaryResponse> resultMutableLiveData = new MutableLiveData<>();
        Call<OrderedSummaryResponse> call = orderSummaryApi.showCartOrderDetails(CLIENT_ID,vendorId,shop_id,user_address_id, MyProfile.getInstance().getUserID(),delivery_method,coupon_code);
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
