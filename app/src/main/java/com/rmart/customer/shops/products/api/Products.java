package com.rmart.customer.shops.products.api;

import com.rmart.BuildConfig;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.shops.list.models.ProductSearchResponce;
import com.rmart.customer.shops.products.model.AddProductToWishListResponse;
import com.rmart.customer.shops.products.model.ProductDetailsDescResponse;
import com.rmart.customer.shops.products.model.ProductsResponce;
import com.rmart.utilits.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Products {


    @GET(BuildConfig.VENDOR_PRODUCTS_LISTS)
    Call<ProductsResponce> getVenderProducts(@Query("client_id") String clientId, @Query("vendor_id") int vendorId, @Query("shop_id") int shop_id,
                                             @Query("customer_id") String customerId, @Query("category_id") String category_id, @Query("product_search_by_name") String product_search_by_name,@Query("start_page") String start_page,@Query("sub_category_id") String sub_category_id,@Query("role_id") String role_id,@Query("productstype") String productstype);

    @POST(BuildConfig.VENDOR_PRODUCT_DETAILS)
    @FormUrlEncoded
    Call<ProductDetailsDescResponse> getVendorProductDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                             @Field("product_id") int productId, @Field("customer_id") String customerId, @Field("role_id") String roleID);
    @POST(BuildConfig.DELETE_PRODUCT_WISH_LIST)
    @FormUrlEncoded
    Call<BaseResponse> deleteProductFromWishList(@Field("client_id") String clientId, @Field("wishlist_id") String wishListId);

    @POST(BuildConfig.VENDOR_MOVE_TO_WISH_LIST)
    @FormUrlEncoded
    Call<AddProductToWishListResponse> moveToWishList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                                      @Field("product_id") int productId, @Field("role_id") String roleID);

    @POST(BuildConfig.VENDOR_ADD_TO_CART)
    @FormUrlEncoded
    Call<AddToCartResponseDetails> addToCart(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                             @Field("product_unit_id") int productUnitId, @Field("product_quantity") int productQuantity, @Field("event") String event, @Field("role_id") String roleID);

    @POST(BuildConfig.CUSTOMER_ALL_SHOPS_PRODUCT)
    @FormUrlEncoded
    Call<ProductSearchResponce> searchProduct(@Field("page") int page, @Field("client_id") String clientId, @Field("latitude") Double latitude, @Field("longitude") Double longitude,
                                              @Field("search_phrase") String search_phrase,@Field("role_id") String roleID);


}
