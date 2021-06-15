package com.rmart.authentication.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.authentication.OnAuthenticationClickedListener;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.customer.models.RSAKeyResponseDetails;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.mapview.MyLocation;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;

import static com.rmart.fcm.MyFirebaseMessagingService.ORDER_ID;

public class AuthenticationActivity extends BaseActivity implements OnAuthenticationClickedListener {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private SupportMapFragment mapFragment;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager locationManager;
    private int REQUEST_CHECK_SETTINGS = 199;
    private String orderID;
    SharedPreferences sharedPreferences;
    String userLoginStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticatin);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),0);
        userLoginStatus = sharedPreferences.getString("userLoginStatus", null);

        if (userLoginStatus != null) {
            if (userLoginStatus.equalsIgnoreCase("yes")) {
                Log.d("userLogin123", userLoginStatus + " ");
                goToCustomerHomeActivity();
            }
        }

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        String mobile_no = "";
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            Log.d("DEEPLINK", deepLink.toString());
                            try {
                                String url[] = deepLink.toString().split(".link/");
                                if (url != null) {
                                    for (int i = 0; i < url.length; i++) {
                                        Log.d("URL", url[1] + " ");
                                        String url_value[] = url[1].split("=");
                                        String param[] = url_value[1].split("/");
                                        Log.d("param", param[0]);
                                        String refer_no = param[0];
                                        Log.d("param", refer_no);
                                        String user_id = url_value[2];
                                        Log.d("User_id", user_id);
                                        RMartApplication rMartApplication = (RMartApplication) getApplicationContext();
                                        rMartApplication.setRefer_no(refer_no);
                                        Log.d("rMarRefer",rMartApplication.getRefer_no());
                                        rMartApplication.setUser_id(user_id);
                                        Log.d("rMarUser",rMartApplication.getUser_id());
                                       /* SharedPreferences sp = getSharedPreferences("ROKAD",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("refer_no", refer_no);
                                        editor.putString("user_id", user_id);
                                        Log.d("User_id",user_id);
                                        editor.commit();
                                        editor.apply();*/
                                    }
                                }
                            } catch (NullPointerException exception) {
                                exception.printStackTrace();
                            }

                        }


                        // Handle the deep link. For example, open the linked content,
                        // or apply promotional credit to the user's account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Auth", "getDynamicLink:onFailure", e);
                    }
                });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = extras.getString(ORDER_ID);
        }
        if (getIntent().getBooleanExtra(getString(R.string.change_password), false)) {
            addFragment(ChangePassword.newInstance("", MyProfile.getInstance(getApplicationContext()).getMobileNumber()), "changePassword", false);
        } else {
            if (BuildConfig.ROLE_ID.equalsIgnoreCase("5")) {
                Log.d("BUILDROLE1",BuildConfig.ROLE_ID);
                addFragment(com.rmart.authentication.login.view.LoginFragment.newInstance("",""), "login", false);
            } else {
                Log.d("BUILDROLE",BuildConfig.ROLE_ID);
                addFragment(com.rmart.authentication.views.LoginFragment.newInstance("", ""), "login", false);
            }
        }


        AuthenticationActivity.super.requestAppPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                R.string.runtime_permissions_txt, 100);

    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
            } else {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    onGPS();
                } else {
                    getLocation();
                }
            }
        });
        task.addOnFailureListener(exception -> {
            try {
                if (exception instanceof ResolvableApiException) {
                    ((ResolvableApiException) exception).startResolutionForResult(AuthenticationActivity.this, REQUEST_CHECK_SETTINGS);
                }
            } catch (IntentSender.SendIntentException ex) {
                // Ignore the error.
            }
        });
    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).setNegativeButton("No", (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {


            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    latitude = task.getResult().getLatitude();
                    longitude = task.getResult().getLongitude();
                } else {
                   /* boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    if (isGPSEnabled) {

                    } else if (isNetworkEnabled) {

                    }*/
                    if (ActivityCompat.checkSelfPermission(AuthenticationActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(AuthenticationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (locationGPS != null) {
                        currentLocation = locationGPS;
                        latitude = locationGPS.getLatitude();
                        longitude = locationGPS.getLongitude();
                    } else {
                        MyLocation myLocation = new MyLocation(AuthenticationActivity.this);
                        myLocation.getLocation(locationResult);
                    }
                }
            });
        }
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            if (location != null) {
                currentLocation = location;
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        }
    };

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

    @Override
    public void goToHomeActivity() {
        Intent intent = new Intent(AuthenticationActivity.this, OrdersActivity.class);
        if (null != orderID) {
            intent.putExtra(ORDER_ID, orderID);
        }
        // intent.putExtra(ORDER_ID, "106");
        startActivity(intent);
    }

    @Override
    public void goToForgotPassword() {
        replaceFragment(ForgotPasswordFragment.getInstance(), ForgotPasswordFragment.class.getName(), true);
    }

    @Override
    public void goToRegistration() {
        replaceFragment(RegistrationFragment.getInstance(), RegistrationFragment.class.getName(), true);
    }

    @Override
    public void validateOTP(String mobileNumber, boolean closePreviousScreen) {
        if (closePreviousScreen) {
            replaceFragment(OTPFragment.newInstance(mobileNumber), OTPFragment.class.getName(), false);
        } else {
            replaceFragment(OTPFragment.newInstance(mobileNumber), OTPFragment.class.getName(), true);
        }

    }

    @Override
    public void changePassword(String otp, String mobileNumber) {
        replaceFragment(ChangePassword.newInstance(otp, mobileNumber), ChangePassword.class.getName(), true);
    }

    @Override
    public void goToProfileActivity(boolean isAddressAdded) {
        Intent intent = new Intent(AuthenticationActivity.this, MyProfileActivity.class);
        intent.putExtra("is_edit", true);
        intent.putExtra("IsNewAddress", isAddressAdded);
        startActivity(intent);
    }

    @Override
    public void validateMailOTP() {

    }

    @Override
    public void goToHomePage() {
        Intent in = new Intent(this, AuthenticationActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();
    }

    @Override
    public void goToCustomerHomeActivity() {
        Intent in = new Intent(this, CustomerHomeActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();
    }

    @Override
    public void proceedToPayment(RSAKeyResponseDetails mobileNumber) {
        replaceFragment(PaymentFragment.newInstance(mobileNumber, ""), PaymentFragment.class.getName(), true);
    }
}