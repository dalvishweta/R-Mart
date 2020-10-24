package com.rmart.customer.views;

import android.os.Bundle;

import com.rmart.baseclass.views.BaseActivity;

/**
 * Created by Satya Seshu on 24/10/20.
 */
public class CustomerFavouritesActivity extends BaseActivity {

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
}
