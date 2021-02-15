package com.rmart.customer;

import com.google.android.gms.maps.model.LatLng;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.utilits.pojos.AddressResponse;

public interface OnCustomerHomeInteractionListener {

    void gotoChangeAddress();

    void gotoVendorProductDetails(ShopDetailsModel customerProductsModel, ProductData productData);

    void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDataDetails, ShopDetailsModel vendorShopDetails);

    void gotoPaymentOptionsScreen(ShopDetailsModel vendorShopDetails, int selectedPaymentType);

    void gotoShoppingCartScreen();

    void gotoShoppingCartDetails(ShoppingCartResponseDetails shoppingCartResponseDetails);

    void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, ShopDetailsModel vendorShopDetails);

    void gotoConfirmedOrderStatusScreen();

    void updateShopWishListStatus(ShopDetailsModel vendorShopDetails);

    void gotoCompleteOrderDetailsScreen(ShopDetailsModel vendorShopDetails);

    void gotoWishListDetailsScreen(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails);

    void gotoEditProfile();
    void gotoEditAddress(AddressResponse address);
    void gotoViewProfile();
    void gotoMapView();
    void getMapGeoCoordinates(LatLng latLng);
}
