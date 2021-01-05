package com.rmart.customer.order.summary.api;

import com.rmart.customer.models.CustomerOrderedResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.rmart.BuildConfig;
import com.rmart.customer.order.summary.model.OrderedSummaryResponse;

public interface OrderSummaryApi {

    @POST(BuildConfig.TEXT_SHOW_CART_ORDER_DETAILS)
    @FormUrlEncoded
    Call<OrderedSummaryResponse> showCartOrderDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shop_id,
                                                      @Field("user_address_id") String user_address_id, @Field("customer_id") String customerId, @Field("delivery_method") String delivery_method, @Field("coupon_code") String coupon_code);
}

