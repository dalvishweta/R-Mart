package com.rmart.customer.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.utilits.pojos.AddressResponse;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListActivity extends BaseNavigationDrawerActivity implements OnCustomerHomeInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.base_container_layout);

        replaceFragment(CustomerWishListFragment.getInstance(), CustomerWishListFragment.class.getName(), false);
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        getToActivity(view.getId(), view.getId() == R.id.my_wish_list);
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
    public void gotoChangeAddress() {

    }

    @Override
    public void gotoVendorProductDetails(ShopDetailsModel customerProductsModel, ProductData productData) {

    }

    @Override
    public void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDataDetails, ShopDetailsModel vendorShopDetails) {

    }

    @Override
    public void gotoPaymentOptionsScreen(ShopDetailsModel vendorShopDetails, int i) {

    }

    @Override
    public void gotoShoppingCartScreen() {

    }

    @Override
    public void gotoShoppingCartDetails(ShoppingCartResponseDetails shoppingCartResponseDetails) {

    }

    @Override
    public void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, ShopDetailsModel vendorShopDetails) {

    }

    @Override
    public void gotoConfirmedOrderStatusScreen() {

    }

    @Override
    public void updateShopWishListStatus(ShopDetailsModel vendorShopDetails) {

    }

    @Override
    public void gotoCompleteOrderDetailsScreen(ShopDetailsModel vendorShopDetails) {

    }

    @Override
    public void gotoWishListDetailsScreen(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails) {
        replaceFragment(CustomerWishListDetailsFragment.getInstance(shopWiseWishListResponseDetails), CustomerWishListDetailsFragment.class.getName(), true);
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
}
