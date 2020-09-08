package com.rmart.baseclass.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.customer.home.views.CustomerHomeActivity;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;
import com.rmart.utilits.Utils;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            /*Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
            startActivity(intent);*/
            SharedPreferences sharedPref;
            if(BuildConfig.FLAVOR.equalsIgnoreCase(Utils.CUSTOMER)) {
                sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String uid = sharedPref.getString(getString(R.string.uid), "-1");
                String aid = sharedPref.getString(getString(R.string.aid), "-1");
                assert aid != null;
                if (!aid.equalsIgnoreCase( "-1")) {
                    Intent intent = new Intent(SplashScreen.this, CustomerHomeActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                    startActivity(intent);
                    /*MyProfile myProfile = MyProfile.getInstance();
                    if (null == myProfile || null == myProfile.getRoleID()) {
                        Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                        startActivity(intent);
                    } else {
                        if (MyProfile.getInstance().getAddressResponses()== null || MyProfile.getInstance().getAddressResponses().size() <= 0) {
                            Intent intent = new Intent(SplashScreen.this, MyProfileActivity.class);
                            intent.putExtra(getString(R.string.is_edit), true);
                            startActivity(intent);
                        }
                    }*/
                }

            } else {
                Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                startActivity(intent);
            }
            /*switch (BuildConfig.FLAVOR) {
                case :



                    break;
                case Utils.CUSTOMER:

                    break;
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