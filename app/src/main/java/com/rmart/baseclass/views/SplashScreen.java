package com.rmart.baseclass.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
            startActivity(intent);
            /*MyProfile myProfile = MyProfile.getInstance();
            if(null == myProfile || null == myProfile.getRoleType()) {
                Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                startActivity(intent);
            } else {
                if (MyProfile.getInstance().getMyLocations() == null || MyProfile.getInstance().getMyLocations().size() <= 0) {
                    Intent intent = new Intent(SplashScreen.this, MyProfileActivity.class);
                    intent.putExtra(getString(R.string.is_edit), true);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SplashScreen.this, OrdersActivity.class));
                }
            }*/
            finish();
        },1000);
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