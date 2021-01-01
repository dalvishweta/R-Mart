package com.rmart.customer.shops.products.api;

import com.rmart.BuildConfig;
import com.rmart.customer.shops.home.model.ShopHomePageResponce;
import com.rmart.customer.shops.products.model.ProductsResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Products {


    @GET(BuildConfig.VENDOR_PRODUCTS_LISTS)
    Call<ProductsResponce> getVenderProducts(@Query("client_id") String clientId, @Query("vendor_id") int vendorId, @Query("shop_id") int shop_id,
                                             @Query("customer_id") String customerId, @Query("category_id") String category_id, @Query("product_search_by_name") String product_search_by_name,@Query("start_page") String start_page,@Query("sub_category_id") String sub_category_id);
}
