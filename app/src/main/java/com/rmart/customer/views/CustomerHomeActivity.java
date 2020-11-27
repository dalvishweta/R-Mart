package com.rmart.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.pojos.AddressResponse;

import java.util.Timer;
import java.util.TimerTask;

public class CustomerHomeActivity extends BaseNavigationDrawerActivity implements OnCustomerHomeInteractionListener {

    private VendorShopsListFragment vendorShopsListFragment;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_authentication);
        Bundle data = getIntent().getExtras();
        vendorShopsListFragment = VendorShopsListFragment.getInstance();
        if (data != null) {
            //addFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), true);
            boolean isShoppingCart = data.getBoolean("ShoppingCart");
            boolean isFavouritesSelected = data.getBoolean("IsFavourites", false);
            if (isShoppingCart) {
                replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), false);
            } else if (isFavouritesSelected) {
                replaceFragment(CustomerFavouritesFragment.getInstance(), CustomerFavouritesFragment.class.getName(), false);
            }
        } else {
            if(!vendorShopsListFragment.isAdded()) {
                replaceFragment(vendorShopsListFragment, VendorShopsListFragment.class.getName(), false);
            }
        }
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    @Override
    public void onClick(View view) {
        getToActivity(view.getId(), view.getId() == R.id.customer_shopping);
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
        vendorShopsListFragment.updateShopWishListStatus(vendorShopDetails);
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
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle data = intent.getExtras();
        if (data != null) {
            boolean isShoppingCart = data.getBoolean("ShoppingCart");
            boolean isFavouritesSelected = data.getBoolean("IsFavourites", false);
            if (isShoppingCart) {
                replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), true);
            } else if (isFavouritesSelected) {
                replaceFragment(CustomerFavouritesFragment.getInstance(), CustomerFavouritesFragment.class.getName(), true);
            }
        } else {
            if(!vendorShopsListFragment.isAdded()) {
                replaceFragment(vendorShopsListFragment, VendorShopsListFragment.class.getName(), false);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer = new Timer();
        LoggerInfo.printLog("Main", "Invoking logout timer");
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.schedule(logoutTimeTask, 6000000); // auto logout in 5 minutes
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            timer = null;
            LoggerInfo.printLog("Main", "cancel timer");
        }
    }

    private class LogOutTimerTask extends TimerTask {

        @Override
        public void run() {
            //redirect user to login screen
            LoggerInfo.printLog("LogOutTimerTask", "run method");
            Intent i = new Intent(CustomerHomeActivity.this, AuthenticationActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }
}
