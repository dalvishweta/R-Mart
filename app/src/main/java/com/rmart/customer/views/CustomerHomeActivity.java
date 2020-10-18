package com.rmart.customer.views;

import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;
import com.rmart.customer.models.ShoppingCartResponseDetails;

public class CustomerHomeActivity extends BaseNavigationDrawerActivity implements OnCustomerHomeInteractionListener {

    private VendorShopsListFragment vendorShopsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_authenticatin);
        Bundle data = getIntent().getExtras();
        vendorShopsListFragment = VendorShopsListFragment.getInstance();
        if (data != null) {
            //addFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), true);
            boolean isShoppingCart = data.getBoolean("ShoppingCart");
            if(isShoppingCart) {
                replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), false);
            }
        } else {
            replaceFragment(vendorShopsListFragment, VendorShopsListFragment.class.getName(), false);
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
        replaceFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), true);
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
        replaceFragment(CustomerWishListDetailsFragment.getInstance(shopWiseWishListResponseDetails), CustomerWishListDetailsFragment.class.getName(), true);
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
}
