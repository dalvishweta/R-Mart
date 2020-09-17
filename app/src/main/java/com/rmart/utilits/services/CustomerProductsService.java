package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.ProductDetailsDescResponse;
import com.rmart.customer.models.ProductInCartResponse;
import com.rmart.customer.models.ShoppingCartResponse;
import com.rmart.customer.models.VendorProductDetailsResponse;
import com.rmart.customer.models.CustomerProductsResponse;
import com.rmart.utilits.pojos.BaseResponse;

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


    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId);

    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("category_id") int categoryId, @Field("start_page") int currentPage);

    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("start_page") int currentPage, @Field("product_search_by_name") String searchShopName);

    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("category_id") int categoryId, @Field("start_page") int currentPage,
                                                            @Field("product_search_by_name") String searchShopName);

    @POST(BuildConfig.VENDOR_PRODUCT_DETAILS)
    @FormUrlEncoded
    Call<ProductDetailsDescResponse> getVendorProductDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                             @Field("product_id") int productId, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_SHOW_ADD_TO_CART)
    @FormUrlEncoded
    Call<ProductInCartResponse> getVendorShowCartList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_DELETE_ADD_TO_CART)
    @FormUrlEncoded
    Call<BaseResponse> deleteProductDetails(@Field("client_id") String clientId, @Field("cart_id") int cartId);

    @POST(BuildConfig.VENDOR_MOVE_TO_WISH_LIST)
    @FormUrlEncoded
    Call<BaseResponse> moveToWishList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") int customerId,
                                                      @Field("product_id") int productId);

    @POST(BuildConfig.VENDORS_SHOW_SHOP_WISE_CART)
    @FormUrlEncoded
    Call<ShoppingCartResponse> getShoppingCartList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_ADD_TO_CART)
    @FormUrlEncoded
    Call<AddToCartResponseDetails> addToCart(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                             @Field("product_unit_id") int productUnitId, @Field("product_quantity") int productQuantity);

    @POST(BuildConfig.VENDOR_ADD_TO_CART)
    @FormUrlEncoded
    Call<AddToCartResponseDetails> removeFromCart(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                             @Field("product_unit_id") int productUnitId, @Field("product_quantity") int productQuantity);

    @POST(BuildConfig.VENDOR_ADD_TO_CART)
    @FormUrlEncoded
    Call<AddToCartResponseDetails> getWishListDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                                  @Field("product_unit_id") int productUnitId, @Field("product_quantity") int productQuantity);

}
