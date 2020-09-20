package com.rmart.customer.views;

import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShoppingCartResponseDetails;

public class CustomerHomeActivity extends BaseNavigationDrawerActivity implements OnCustomerHomeInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_authenticatin);

        showCartIcon();
        addFragment(VendorShopsListFragment.getInstance(), VendorShopsListFragment.class.getName(),false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.customer_shopping) {
            getToActivity(view.getId(), false);
        } else {
            getToActivity(view.getId(), true);
        }
    }

    @Override
    public void gotoChangeAddress() {
        addFragment(ChangeAddressFragment.getInstance(),ChangeAddressFragment.class.getName(),true);
    }

    @Override
    public void gotoVendorProductDetails(CustomerProductsShopDetailsModel customerProductsModel) {
        showCartIcon();
        addFragment(VendorProductDetailsFragment.getInstance(customerProductsModel),VendorProductDetailsFragment.class.getName(),true);
    }

    @Override
    public void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDetails, CustomerProductsShopDetailsModel vendorShopDetails) {
        showCartIcon();
        addFragment(ProductCartDetailsFragment.getInstance(vendorProductDetails, vendorShopDetails), ProductCartDetailsFragment.class.getName(), true);
    }

    @Override
    public void gotoShoppingCartScreen() {
        hideCartIcon();
        addFragment(ShoppingCartFragment.getInstance(), ShoppingCartFragment.class.getName(), true);
    }

    @Override
    public void gotoSelectedShopDetails(ShoppingCartResponseDetails shoppingCartResponseDetails) {
        hideCartIcon();
        addFragment(ConfirmOrderFragment.getInstance(shoppingCartResponseDetails), ConfirmOrderFragment.class.getName(), true);
    }

    @Override
    public void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, CustomerProductsShopDetailsModel vendorShopDetails) {
        showCartIcon();
        addFragment(VendorSameProductsListScreen.getInstance(productCategoryDetails, vendorShopDetails), VendorSameProductsListScreen.class.getName(), true);
    }

    @Override
    public void gotoConfirmedOrderStatusScreen() {

    }

    @Override
    public void gotoPaymentOptionsScreen(CustomerProductsShopDetailsModel vendorShopDetails) {
        hideCartIcon();
        addFragment(PaymentOptionsFragment.getInstance(vendorShopDetails), PaymentOptionsFragment.class.getName(), true);
    }

    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }
}
