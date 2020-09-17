package com.rmart.customer.views;

import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListActivity extends BaseNavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container_layout);

        addFragment(CustomerWishListFragment.getInstance(), CustomerWishListFragment.class.getName(), true);
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
}
