package com.rmart.customer.views;

import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.CustomerWishListDetailsFragment;
import com.rmart.customer.OnCustomerWishListInteractionListener;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListActivity extends BaseNavigationDrawerActivity implements OnCustomerWishListInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.base_container_layout);

        replaceFragment(CustomerWishListFragment.getInstance(), CustomerWishListActivity.class.getName(), false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.retailer_orders) {
            getToActivity(view.getId(), false);
        } else {
            getToActivity(view.getId(), true);
        }
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
    public void gotoWishListDetailsScreen(ShopWiseWishListResponseDetails shopWiseWishListResponseDetails) {
        replaceFragment(CustomerWishListDetailsFragment.getInstance(shopWiseWishListResponseDetails), CustomerWishListDetailsFragment.class.getName(), true);
    }
}
