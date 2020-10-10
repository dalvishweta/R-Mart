package com.rmart.profile.views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.model.LatLng;
import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.mapview.MapsFragment;
import com.rmart.profile.OnMyProfileClickedListener;
import com.rmart.profile.model.MyAddress;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.viewmodels.AddressViewModel;
import com.rmart.utilits.pojos.AddressResponse;

public class MyProfileActivity extends BaseActivity implements OnMyProfileClickedListener {

    private boolean isAddNewAddress = false;
    private boolean isFromLogin = false;
    private EditAddressFragment editAddressFragment;

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
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setId(-1);
            editAddressFragment = EditAddressFragment.newInstance(isAddNewAddress, addressResponse);
            replaceFragment(editAddressFragment, EditAddressFragment.class.getName(), false);
        } else {
            replaceFragment(ViewMyProfileFragment.newInstance(), ViewMyProfileFragment.class.getName(), false);
        }

        // MyProfileViewModel myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
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
        replaceFragment(EditMyProfileFragment.newInstance(false), EditMyProfileFragment.class.getName(), true);
    }

    @Override
    public void gotoEditAddress(AddressResponse address) {
        editAddressFragment = EditAddressFragment.newInstance(false, address);
        replaceFragment(editAddressFragment, EditAddressFragment.class.getName(), true);
    }

    @Override
    public void gotoViewProfile() {
        replaceFragment(ViewMyProfileFragment.newInstance(), ViewMyProfileFragment.class.getName(), false);
    }

    @Override
    public void gotoMapView() {
        replaceFragment(MapsFragment.newInstance(true, ""), MapsFragment.class.getName(), false);
    }

    @Override
    public void getMapGeoCoordinates(LatLng latLng) {
        editAddressFragment.updateLocationDetails(latLng);
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
}