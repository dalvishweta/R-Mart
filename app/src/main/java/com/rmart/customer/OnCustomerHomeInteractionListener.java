package com.rmart.customer;

import com.google.android.gms.maps.model.LatLng;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.utilits.pojos.AddressResponse;

public interface OnCustomerHomeInteractionListener {

    void gotoChangeAddress();

    void gotoVendorProductDetails(CustomerProductsShopDetailsModel customerProductsModel);

    void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDataDetails, CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoPaymentOptionsScreen(CustomerProductsShopDetailsModel vendorShopDetails,int selectedPaymentType);

    void gotoShoppingCartScreen();

    void gotoShoppingCartDetails(ShoppingCartResponseDetails shoppingCartResponseDetails);

    void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoConfirmedOrderStatusScreen();

    void updateShopWishListStatus(CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoCompleteOrderDetailsScreen(CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoWishListDetailsScreen(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails);

    void gotoEditProfile();
    void gotoEditAddress(AddressResponse address);
    void gotoViewProfile();
    void gotoMapView();
    void getMapGeoCoordinates(LatLng latLng);
}
