package com.rmart.baseclass.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
            startActivity(intent);
            /*if(null == MyProfile.getInstance()) {

            } else {
                if (MyProfile.getInstance().getDob() == null || MyProfile.getInstance().getDob().length() <= 0) {
                    Intent intent = new Intent(SplashScreen.this, ProfileActivity.class);
                    intent.putExtra(getString(R.string.is_edit), true);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
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
}