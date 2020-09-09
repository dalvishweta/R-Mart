package com.rmart.authentication.views;

import android.content.Intent;
import android.os.Bundle;

import com.rmart.R;
import com.rmart.authentication.OnAuthenticationClickedListener;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;

public class AuthenticationActivity extends BaseActivity implements OnAuthenticationClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticatin);
        if(getIntent().getBooleanExtra(getString(R.string.change_password), false)) {
            addFragment(ChangePassword.newInstance("", MyProfile.getInstance().getMobileNumber()), "changePassword", false);
        } else {
            addFragment(LoginFragment.newInstance("",""), "login", false);
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
    public void goToHomeActivity() {
        Intent intent = new Intent(AuthenticationActivity.this, OrdersActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToForgotPassword() {
        replaceFragment(ForgotPasswordFragment.newInstance("", ""), "ForgotPasswordFragment", true);
    }

    @Override
    public void goToRegistration() {
        replaceFragment(RegistrationFragment.newInstance("", ""), "RegistrationFragment", true);
    }

    @Override
    public void validateOTP(String mobileNumber) {
        replaceFragment(OTPFragment.newInstance(mobileNumber, ""), "OTPFragment", true);
    }

    @Override
    public void changePassword(String otp, String mobileNumber) {
        replaceFragment(ChangePassword.newInstance(otp, mobileNumber), "changePassword", true);
    }

    @Override
    public void goToProfileActivity() {
        Intent intent = new Intent(AuthenticationActivity.this, MyProfileActivity.class);
        intent.putExtra("is_edit", true);
        startActivity(intent);
    }

    @Override
    public void validateMailOTP() {

    }

    @Override
    public void goToHomePage() {
        Intent in = new Intent(this, AuthenticationActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(in);
        finish();
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
    }

    @Override
    public void goToCustomerHomeActivity() {
        Intent in = new Intent(this, CustomerHomeActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(in);
        finish();
    }


}