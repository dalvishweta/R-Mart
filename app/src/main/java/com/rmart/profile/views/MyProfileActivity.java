package com.rmart.profile.views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.mapview.MapsFragment;
import com.rmart.profile.OnMyProfileClickedListener;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.viewmodels.AddressViewModel;
import com.rmart.utilits.pojos.AddressResponse;

public class MyProfileActivity extends BaseActivity implements OnMyProfileClickedListener {

    private boolean isAddNewAddress = false;
    private boolean isFromLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.profile_details);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            isFromLogin = extras.getBoolean("is_edit", false);
            isAddNewAddress = extras.getBoolean("IsNewAddress", false);
        }

        new ViewModelProvider(this).get(AddressViewModel.class);

        if (MyProfile.getInstance().getPrimaryAddressId() == null || isAddNewAddress) {
            addFragment(EditAddressFragment.newInstance(isAddNewAddress, null), "EditAddressFragment", false);
        } else {
            addFragment(ViewMyProfileFragment.newInstance(), "ViewMyProfileFragment", false);
        }

        // MyProfileViewModel myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onClick(View view) {
        if (view.getId() != R.id.update_profile) {
            getToActivity(view.getId(), false);
        } else {
            getToActivity(view.getId(), true);
        }
    }*/

    @Override
    public void gotoEditProfile() {
        addFragment(EditMyProfileFragment.newInstance(false), "EditMyProfileFragment", true);
    }

    @Override
    public void gotoEditAddress(AddressResponse address) {
        addFragment(EditAddressFragment.newInstance(false, address), "EditAddressFragment", true);
    }

    @Override
    public void gotoViewProfile() {
        addFragment(ViewMyProfileFragment.newInstance(), "ViewProfile", true);
    }

    @Override
    public void gotoMapView() {
        addFragment(MapsFragment.newInstance(true, "profile"), "MapsFragment", true);
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