package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.customer.models.VendorProductDetailsResponse;
import com.rmart.customer.models.CustomerProductsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Satya Seshu on 08/09/20.
 */
public interface CustomerProductsService {

    @POST(BuildConfig.CUSTOMER_SHOPS_LIST)
    @FormUrlEncoded
    Call<CustomerProductsResponse> getCustomerShopsList(@Field("client_id") String clientId,
                                                        @Field("start_page") int currentPage, @Field("search_param") String searchShopName);


    @POST(BuildConfig.VENDOR_PRODUCT_DETAILS)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId);

    @POST(BuildConfig.VENDOR_PRODUCT_DETAILS)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("category_id") int categoryId, @Field("start_page") int currentPage);

    @POST(BuildConfig.VENDOR_PRODUCT_DETAILS)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("start_page") int currentPage, @Field("product_search_by_name") String searchShopName);

    @POST(BuildConfig.VENDOR_PRODUCT_DETAILS)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("category_id") int categoryId, @Field("start_page") int currentPage,
                                                            @Field("product_search_by_name") String searchShopName);
}
