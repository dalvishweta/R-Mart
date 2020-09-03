package com.rmart.profile.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.mapview.MapsFragment;
import com.rmart.profile.OnMyProfileClickedListener;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.viewmodels.AddressViewModel;
import com.rmart.utilits.pojos.AddressResponse;

public class MyProfileActivity extends BaseNavigationDrawerActivity implements OnMyProfileClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ViewModelProvider(this).get(AddressViewModel.class);
        Intent intent = getIntent();
        boolean isFromLogin =false;
        if(intent!= null) {
            isFromLogin = intent.getBooleanExtra("is_edit", false);
        }

        if(MyProfile.getInstance().getPrimaryAddressId() == null) {
            addFragment(EditAddressFragment.newInstance(isFromLogin, null), "EditAddressFragment", false);
        } else {
            addFragment(ViewMyProfileFragment.newInstance("", ""), "ViewMyProfileFragment", false);
        }


        // MyProfileViewModel myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.update_profile) {
            getToActivity(view.getId(), false);
        } else {
            getToActivity(view.getId(), true);
        }
    }

    @Override
    public void gotoEditProfile() {
        replaceFragment(EditMyProfileFragment.newInstance(false, ""), "EditMyProfileFragment", true);
    }

    @Override
    public void gotoEditAddress(AddressResponse address) {
        replaceFragment(EditAddressFragment.newInstance(false, address), "EditAddressFragment", true);
    }

    @Override
    public void gotoViewProfile() {
        replaceFragment(ViewMyProfileFragment.newInstance("", ""), "ViewProfile", true);
    }

    @Override
    public void gotoMapView() {
        replaceFragment(MapsFragment.newInstance(true, "profile"), "MapsFragment", true);
    }
}