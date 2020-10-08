package com.rmart.baseclass.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.LoginDetailsModel;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.RokadMartCache;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.pojos.ProfileResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends BaseActivity {

    private Handler delayHandler = new Handler(Looper.getMainLooper());
    private String deviceToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            deviceToken = instanceIdResult.getToken();
            LoggerInfo.printLog("FCM Token", deviceToken);
        });

        if (BuildConfig.FLAVOR.equalsIgnoreCase(Utils.CUSTOMER)) {
            Object lObject = RokadMartCache.getData(Constants.CACHE_CUSTOMER_DETAILS, this);
            if (lObject == null) {
                setDelayHandler();
            } else {
                getLoginDetails((LoginDetailsModel) lObject);
            }
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(Utils.RETAILER)) {
            /*Object lObject = RokadMartCache.getData(Constants.CACHE_RETAILER_DETAILS, this);
            if (lObject == null) {
                setDelayHandler();
            } else {
                getLoginDetails((ProfileResponse) lObject);
            }*/
            setDelayHandler();
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(Utils.DELIVERY)) {
            Object lObject = RokadMartCache.getData(Constants.CACHE_DELIVERY_DETAILS, this);
            if (lObject == null) {
                setDelayHandler();
            } else {
                getLoginDetails((LoginDetailsModel) lObject);
            }
        } else {
            setDelayHandler();
        }
    }

    private void getLoginDetails(LoginDetailsModel loginDetails) {
        if (Utils.isNetworkConnected(this)) {
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            authenticationService.login(deviceToken, loginDetails.getMobileNumber(), loginDetails.getPassword()).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        LoginResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                if (data.getLoginData().getRoleID().equalsIgnoreCase(getString(R.string.role_id))) {
                                    try {
                                        ProfileResponse profileResponse = data.getLoginData();
                                        MyProfile.setInstance(profileResponse);
                                        MyProfile.getInstance().setCartCount(profileResponse.getTotalCartCount());
                                        if (MyProfile.getInstance().getPrimaryAddressId() == null) {
                                            gotoProfileActivity();
                                        } else {
                                            switch (data.getLoginData().getRoleID()) {
                                                case Utils.CUSTOMER_ID:
                                                    gotoCustomerHomeActivity();
                                                    break;
                                                case Utils.RETAILER_ID:
                                                    gotoHomeActivity();
                                                    break;
                                                case Utils.DELIVERY_ID:
                                                    gotoDeliveryActivity();
                                                    break;
                                            }
                                        }
                                    } catch (Exception ex) {
                                        gotoAuthenticationActivity();
                                    }
                                } else {
                                    gotoAuthenticationActivity();
                                }
                            } else {
                                gotoAuthenticationActivity();
                            }
                        } else {
                            gotoAuthenticationActivity();
                        }
                    } else {
                        gotoAuthenticationActivity();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    gotoAuthenticationActivity();
                }
            });
        } else {
            gotoAuthenticationActivity();
        }
    }

    private void setDelayHandler() {
        delayHandler.postDelayed(runnable, 1000);
    }

    private Runnable runnable = () -> {
        if (!isFinishing()) {
            gotoAuthenticationActivity();
        }
    };

    private void gotoAuthenticationActivity() {
        startActivity(new Intent(SplashScreen.this, AuthenticationActivity.class));
        finish();
    }

    private void gotoDeliveryActivity() {
        startActivity(new Intent(SplashScreen.this, OrdersActivity.class));
        finish();
    }

    private void gotoCustomerHomeActivity() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.uid), Utils.CUSTOMER_ID);
        editor.apply();
        startActivity(new Intent(SplashScreen.this, CustomerHomeActivity.class));
        finish();
    }

    private void gotoHomeActivity() {
        startActivity(new Intent(SplashScreen.this, OrdersActivity.class));
        finish();
    }

    private void gotoProfileActivity() {
        Intent intent = new Intent(this, MyProfileActivity.class);
        intent.putExtra("is_edit", true);
        intent.putExtra("IsNewAddress", true);
        startActivity(intent);
        finish();
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