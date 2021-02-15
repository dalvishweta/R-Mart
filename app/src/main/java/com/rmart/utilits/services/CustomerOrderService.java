package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductResponse;
import com.rmart.utilits.pojos.orders.OrderProductListResponse;
import com.rmart.utilits.pojos.orders.OrderStateListResponse;
import com.rmart.utilits.pojos.orders.OrdersByStatus;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CustomerOrderService {

    @POST(BuildConfig.CUSTOMER_VIEW_ALL_ORDERS)
    @FormUrlEncoded
    Call<OrdersByStatus> getStateOfOrder(@Field("start_index") String startIndex,
                                         @Field("customer_mobile") String customerMobile);

    @POST(BuildConfig.CUSTOMER_VIEW_ORDER_BY_ID)
    @FormUrlEncoded
    Call<CustomerOrderProductResponse> viewOrderById(@Field("order_id") String orderID,
                                                     @Field("customer_mobile") String customerMobile,@Field("role_id") String roleID);
    @POST(BuildConfig.CUSTOMER_VIEW_ORDER_BY_ID)
    @FormUrlEncoded
    Call<OrderProductListResponse> updateOrderStatus(@Field("order_id")String orderID,
                                                     @Field("user_id") String id,
                                                     @Field("status") String newOrderStatus,@Field("role_id") String roleID);

    @POST(BuildConfig.CUSTOMER_REORDER_PRODUCTS)
    @FormUrlEncoded
    Call<CustomerOrderProductResponse> getUpdatedProductDetails(@Field("order_id")String orderID,
                                                     @Field("customer_user_id") String userID,
                                                     @Field("customer_mobile") String customerMobile,
                                                     @Field("vendor_mobile") String vendorNumber);

}
