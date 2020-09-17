package com.rmart.customer.views;

import android.os.Bundle;

import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container_layout);

        addFragment(CustomerWishListFragment.getInstance(), CustomerWishListFragment.class.getName(), true);
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
