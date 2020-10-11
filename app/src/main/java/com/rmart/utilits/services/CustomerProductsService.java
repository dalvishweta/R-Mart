package com.rmart.utilits.services;

import com.rmart.BuildConfig;
import com.rmart.customer.models.AddProductToWishListResponse;
import com.rmart.customer.models.AddShopToWishListResponse;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.CustomerOrderedResponseModel;
import com.rmart.customer.models.CustomerProductsResponse;
import com.rmart.customer.models.ProductDetailsDescResponse;
import com.rmart.customer.models.ProductInCartResponse;
import com.rmart.customer.models.ProductOrderedResponseModel;
import com.rmart.customer.models.ShopWiseWishListResponseModel;
import com.rmart.customer.models.ShoppingCartResponse;
import com.rmart.customer.models.VendorProductDetailsResponse;
import com.rmart.customer.models.WishListResponseModel;
import com.rmart.customer_order.models.OrderAgainProductModel;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.pojos.orders.Product;

import java.util.List;

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
    Call<CustomerProductsResponse> getCustomerShopsList(@Field("client_id") String clientId, @Field("start_page") int currentPage, @Field("search_param") String searchShopName,
                                                        @Field("customer_id") String customerId, @Field("latitude") double latitude, @Field("longitude") double longitude);


    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("category_id") int categoryId, @Field("start_page") int currentPage, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("start_page") int currentPage, @Field("product_search_by_name") String searchShopName,
                                                            @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_PRODUCTS_LIST)
    @FormUrlEncoded
    Call<VendorProductDetailsResponse> getVendorShopDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                            @Field("category_id") int categoryId, @Field("start_page") int currentPage,
                                                            @Field("product_search_by_name") String searchShopName, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_PRODUCT_DETAILS)
    @FormUrlEncoded
    Call<ProductDetailsDescResponse> getVendorProductDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shopId,
                                                             @Field("product_id") int productId, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_SHOW_ADD_TO_CART)
    @FormUrlEncoded
    Call<ProductInCartResponse> getVendorShowCartList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_DELETE_ADD_TO_CART)
    @FormUrlEncoded
    Call<ProductInCartResponse> deleteProductDetails(@Field("client_id") String clientId, @Field("cart_id") int cartId);

    @POST(BuildConfig.VENDOR_MOVE_TO_WISH_LIST)
    @FormUrlEncoded
    Call<AddProductToWishListResponse> moveToWishList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                                      @Field("product_id") int productId);

    @POST(BuildConfig.VENDORS_SHOW_SHOP_WISE_CART)
    @FormUrlEncoded
    Call<ShoppingCartResponse> getShoppingCartList(@Field("client_id") String clientId, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_ADD_TO_CART)
    @FormUrlEncoded
    Call<AddToCartResponseDetails> addToCart(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                             @Field("product_unit_id") int productUnitId, @Field("product_quantity") int productQuantity, @Field("event") String event);

    @POST(BuildConfig.VENDORS_SHOW_SHOP_WISE_WISH_LIST_CART)
    @FormUrlEncoded
    Call<ShopWiseWishListResponseModel> getShowShopWiseWishListData(@Field("client_id") String clientId, @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDOR_REMOVE_FROM_CART)
    @FormUrlEncoded
    Call<BaseResponse> removeFromCart(@Field("client_id") String clientId, @Field("cart_id") int cartId);

    @POST(BuildConfig.VENDORS_SHOW_WISH_LIST_CART)
    @FormUrlEncoded
    Call<WishListResponseModel> getShowWishListData(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("start_page") int startPage,
                                                    @Field("customer_id") String customerId);

    @POST(BuildConfig.VENDORS_SAVE_PLACE_ORDER)
    @FormUrlEncoded
    Call<ProductOrderedResponseModel> savePlaceToOrder(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("user_address_id") String userAddressId,
                                                       @Field("customer_id") String customerId, @Field("shop_id") int shop_id, @Field("mode_of_payment_id") int modeOfPaymentId);

    @POST(BuildConfig.ADD_SHOP_TO_WISH_LIST)
    @FormUrlEncoded
    Call<AddShopToWishListResponse> addShopToWishList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shop_id,
                                                      @Field("customer_id") String customerId);

    @POST(BuildConfig.DELETE_SHOP_TO_WISH_LIST)
    @FormUrlEncoded
    Call<BaseResponse> deleteShopFromWishList(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shop_id,
                                              @Field("customer_id") String customerId);

    @POST(BuildConfig.DELETE_PRODUCT_WISH_LIST)
    @FormUrlEncoded
    Call<BaseResponse> deleteProductFromWishList(@Field("client_id") String clientId, @Field("wishlist_id") String wishListId);

    @POST(BuildConfig.SHOW_CART_ORDER_DETAILS)
    @FormUrlEncoded
    Call<CustomerOrderedResponseModel> showCartOrderDetails(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("shop_id") int shop_id,
                                                            @Field("user_address_id") String user_address_id, @Field("customer_id") String customerId);

    @POST(BuildConfig.ADD_RE_ORDER_TO_CART)
    @FormUrlEncoded
    Call<AddToCartResponseDetails> addReOrderToCart(@Field("client_id") String clientId, @Field("vendor_id") int vendorId, @Field("customer_id") String customerId,
                                                    @Field("product_arr") List<OrderAgainProductModel> productList);

}