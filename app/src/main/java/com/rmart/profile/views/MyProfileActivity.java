package com.rmart.profile.views;

import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.mapview.MapsFragment;
import com.rmart.profile.OnMyProfileClickedListener;

public class MyProfileActivity extends BaseNavigationDrawerActivity implements OnMyProfileClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MyProfileViewModel myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
        addFragment(ViewMyProfileFragment.newInstance("", ""), "ViewMyProfileFragment", false);
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
        replaceFragment(EditMyProfileFragment.newInstance("", ""), "EditMyProfileFragment", true);
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