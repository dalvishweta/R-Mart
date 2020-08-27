package com.rmart.profile.views;

import android.content.Intent;
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
        Intent intent = getIntent();
        boolean isFromLogin =false;
        if(intent!= null) {
            isFromLogin = intent.getBooleanExtra("is_edit", false);
        }

        if(isFromLogin) {
            addFragment(EditMyProfileFragment.newInstance(isFromLogin, ""), "EditMyProfileFragment", false);
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
    public void gotoViewProfile() {
        replaceFragment(ViewMyProfileFragment.newInstance("", ""), "ViewProfile", true);
    }

    @Override
    public void gotoMapView() {
        replaceFragment(MapsFragment.newInstance(true, "profile"), "MapsFragment", true);
    }
}