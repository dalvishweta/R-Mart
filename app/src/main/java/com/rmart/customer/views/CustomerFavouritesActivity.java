package com.rmart.customer.views;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.utilits.pojos.AddressResponse;

/**
 * Created by Satya Seshu on 24/10/20.
 */
public class CustomerFavouritesActivity extends BaseNavigationDrawerActivity implements OnCustomerHomeInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(CustomerFavouritesFragment.getInstance(), CustomerFavouritesFragment.class.getName(), false);
    }

    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }

    @Override
    public void hideHamburgerIcon() {

    }

    @Override
    public void showHamburgerIcon() {

    }

    @Override
    public void showCartIcon() {

    }

    @Override
    public void hideCartIcon() {

    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    @Override
    public void gotoChangeAddress() {
        replaceFragment(ChangeAddressFragment.getInstance(),ChangeAddressFragment.class.getName(),true);
    }

    @Override
    public void gotoVendorProductDetails(CustomerProductsShopDetailsModel customerProductsModel) {
        showCartIcon();
        replaceFragment(VendorProductDetailsFragment.getInstance(customerProductsModel), VendorProductDetailsFragment.class.getName(), true);
    }

    @Override
    public void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDetails, CustomerProductsShopDetailsModel vendorShopDetails) {
        showCartIcon();
        replaceFragment(ProductCartDetailsFragment.getInstance(vendorProductDetails, vendorShopDetails), ProductCartDetailsFragment.class.getName(), true);
    }

    @Override
    public void gotoShoppingCartScreen() {
        hideCartIcon();
        replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), false);
    }

    @Override
    public void gotoShoppingCartDetails(ShoppingCartResponseDetails shoppingCartResponseDetails) {
        hideCartIcon();
        replaceFragment(ShoppingCartDetailsFragment.getInstance(shoppingCartResponseDetails), ShoppingCartDetailsFragment.class.getName(), true);
    }

    @Override
    public void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, CustomerProductsShopDetailsModel vendorShopDetails) {
        showCartIcon();
        replaceFragment(VendorSameProductsListScreen.getInstance(productCategoryDetails, vendorShopDetails), VendorSameProductsListScreen.class.getName(), true);
    }

    @Override
    public void gotoConfirmedOrderStatusScreen() {

    }

    @Override
    public void updateShopWishListStatus(CustomerProductsShopDetailsModel vendorShopDetails) {

    }

    @Override
    public void gotoCompleteOrderDetailsScreen(CustomerProductsShopDetailsModel vendorShopDetails) {
        hideCartIcon();
        replaceFragment(CustomerOrderDetailsFragment.getInstance(vendorShopDetails), PaymentOptionsFragment.class.getName(), true);
    }

    @Override
    public void gotoWishListDetailsScreen(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails) {

    }

    @Override
    public void gotoEditProfile() {

    }

    @Override
    public void gotoEditAddress(AddressResponse address) {

    }

    @Override
    public void gotoViewProfile() {

    }

    @Override
    public void gotoMapView() {

    }

    @Override
    public void getMapGeoCoordinates(LatLng latLng) {

    }

    @Override
    public void gotoPaymentOptionsScreen(CustomerProductsShopDetailsModel vendorShopDetails) {
        hideCartIcon();
        replaceFragment(PaymentOptionsFragment.getInstance(vendorShopDetails), PaymentOptionsFragment.class.getName(), true);
    }

    @Override
    public void onClick(View view) {
        getToActivity(view.getId(), view.getId() == R.id.my_favourites_list);
    }
}
