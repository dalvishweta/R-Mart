package com.rmart.baseclass.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.baseclass.Constants;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RokadMartCache;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ProfileResponse;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            /*Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
            startActivity(intent);*/
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
                String token = instanceIdResult.getToken();
                Log.i("FCM Token", "FCM Token: "+token);
            });

            SharedPreferences sharedPref;
            if(BuildConfig.FLAVOR.equalsIgnoreCase(Utils.CUSTOMER)) {
                /*sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String uid = sharedPref.getString(getString(R.string.uid), "-1");
                String aid = sharedPref.getString(getString(R.string.aid), "-1");
                if (aid != null) {
                    if (!aid.equalsIgnoreCase("-1")) {
                        Intent intent = new Intent(SplashScreen.this, CustomerHomeActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                        startActivity(intent);
                    }
                }*/
                Object lObject = RokadMartCache.getData(Constants.CACHE_CUSTOMER_DETAILS, this);
                if (lObject == null) {
                    Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                    startActivity(intent);
                } else {
                    if (lObject instanceof ProfileResponse) {
                        MyProfile.setInstance((ProfileResponse) lObject);
                        Intent intent = new Intent(SplashScreen.this, CustomerHomeActivity.class);
                        startActivity(intent);
                    }
                }
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(Utils.RETAILER_ID)) {
                Object lObject = RokadMartCache.getData(Constants.CACHE_RETAILER_DETAILS, this);
                if (lObject == null) {
                    Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                    startActivity(intent);
                } else {
                    if (lObject instanceof ProfileResponse) {
                        MyProfile.setInstance((ProfileResponse) lObject);
                        Intent intent = new Intent(SplashScreen.this, OrdersActivity.class);
                        startActivity(intent);
                    }
                }
            } else {
                Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                startActivity(intent);
            }
            finish();
        }, 1000);
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

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