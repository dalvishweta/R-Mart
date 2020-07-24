package com.rmart.authentication.views;

import android.os.Bundle;

import com.rmart.R;
import com.rmart.baseclass.views.BaseActivity;

public class AuthenticationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticatin);
        addFragment(LoginFragment.newInstance("",""), "login", false);
    }

    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }
}