package com.rmart.authentication.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.LoginDetailsModel;
import com.rmart.fcm.MyFirebaseMessagingService;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.RokadMartCache;
import com.rmart.utilits.UpdateCartCountDetails;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.pojos.ProfileResponse;
import com.rmart.utilits.pojos.ResendOTPResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends LoginBaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String mMobileNumber;
    private String mPassword,etPassword;
    private AppCompatEditText  etMobileNumber;
    EditText editTextOne,editTextTwo,editTextThree,editTextFour;
    private String deviceToken;
    ProfileResponse profileResponse;
    LinearLayout layout_opt;
    private ImageView slice,app_logo;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etMobileNumber = view.findViewById(R.id.mobile_number);
        slice = view.findViewById(R.id.slice);
        app_logo = view.findViewById(R.id.app_logo);
        editTextOne = view.findViewById(R.id.editTextOne);
        editTextTwo = view.findViewById(R.id.editTextTwo);
        editTextThree = view.findViewById(R.id.editTextThree);
        editTextFour = view.findViewById(R.id.editTextFour);
        //etPassword = view.findViewById(R.id.password);
        try{


            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            int sliceheight=(height*30)/100;
            int app_logoheight=(height*15)/100;
            slice.getLayoutParams().height = sliceheight;
            app_logo.getLayoutParams().height = app_logoheight;
            app_logo.getLayoutParams().width = app_logoheight;
            //slice.setMaxHeight(sliceheight);
            slice.requestLayout();
            app_logo.requestLayout();



        } catch (Exception e){

        }
        view.findViewById(R.id.login).setOnClickListener(this);
       // view.findViewById(R.id.register).setOnClickListener(this);
        if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.DELIVERY_ID)) {
            view.findViewById(R.id.footer).setVisibility(View.INVISIBLE);
           // view.findViewById(R.id.login_root).setBackgroundResource(R.drawable.authentication_bg_2);
        }
        view.findViewById(R.id.forgot_password).setOnClickListener(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(requireActivity(), instanceIdResult -> {
            deviceToken = instanceIdResult.getToken();
            LoggerInfo.printLog("FCM Token", deviceToken);
        });
        // etMobileNumber.setText("9912592822");
        // etPassword.setText("Qwerty@123");
        deviceToken = MyFirebaseMessagingService.getToken(this.requireContext());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login) {
            mMobileNumber = Objects.requireNonNull(etMobileNumber.getText()).toString().trim();
            etPassword = editTextOne.getText().toString().trim()+editTextTwo.getText().toString().trim()+editTextThree.getText().toString().trim()+editTextFour.getText().toString().trim();
            mPassword = Objects.requireNonNull(etPassword).toString();
            if (TextUtils.isEmpty(mMobileNumber) || !Utils.isValidMobile(mMobileNumber)) {
                showDialog(getString(R.string.error_mobile));
            } else if (TextUtils.isEmpty(mPassword)) {
                showDialog(getString(R.string.error_password));
            } else {
                checkCredentials();

            }
        } else if (view.getId() == R.id.forgot_password) {
            mListener.goToForgotPassword();
        } else  {
            mListener.goToRegistration();
            /*FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.base_container, RegistrationFragment.getInstance(), RegistrationFragment.class.getName());
            fragmentTransaction.addToBackStack( RegistrationFragment.class.getName());
            fragmentTransaction.commit();*/
        }
    }

    private void checkCredentials() {
        if (Utils.isNetworkConnected(requireActivity())){
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            // mPassword = "12345";
            authenticationService.login(deviceToken, mMobileNumber, mPassword, BuildConfig.ROLE_ID).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                if (data.getLoginData().getRoleID().equalsIgnoreCase(getString(R.string.role_id))) {
                                    try {
                                        LoginDetailsModel loginDetailsModel = new LoginDetailsModel();
                                        loginDetailsModel.setMobileNumber(mMobileNumber);
                                        loginDetailsModel.setPassword(mPassword);
                                        profileResponse = data.getLoginData();
                                        checkRegistration(profileResponse,mMobileNumber,mPassword);

                                        MyProfile.setInstance(getActivity(),profileResponse);
                                        //MyProfile.getInstance().setCartCount(profileResponse.getTotalCartCount());
                                        UpdateCartCountDetails.updateCartCountDetails.onNext(profileResponse.getTotalCartCount());
                                        if (MyProfile.getInstance(getActivity()).getPrimaryAddressId() == null) {
                                            mListener.goToProfileActivity(true);
                                        } else {
                                            switch (data.getLoginData().getRoleID()) {
                                                case Utils.CUSTOMER_ID:

                                                    RokadMartCache.putData(Constants.CACHE_CUSTOMER_DETAILS, requireActivity(), loginDetailsModel);
                                                    mListener.goToCustomerHomeActivity();
                                                    SharedPreferences sharedPref = Objects.requireNonNull(requireActivity()).getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.putString(getString(R.string.uid), Utils.CUSTOMER_ID);
                                                    editor.apply();
                                                    break;
                                                case Utils.RETAILER_ID:
                                                    // RokadMartCache.putData(Constants.CACHE_RETAILER_DETAILS, requireActivity(), loginDetailsModel);
                                                    mListener.goToHomeActivity();
                                                    break;
                                                case Utils.DELIVERY_ID:
                                                    // RokadMartCache.putData(Constants.CACHE_DELIVERY_DETAILS, requireActivity(), loginDetailsModel);
                                                    mListener.goToHomeActivity();
                                                    break;
                                            }
                                        }

                                        // mListener.goToProfileActivity();
                                        Objects.requireNonNull(requireActivity()).onBackPressed();
                                    } catch (Exception e) {
                                        if (data.getMsg().contains("role id")) {
                                            showDialog("", getString(R.string.error_role_login));
                                        } else {
                                            showDialog(e.getMessage());
                                        }
                                    }
                                } else {
                                    showDialog("", getString(R.string.error_role_login));
                                }
                            } else {
                                if (data.getMsg().contains("role id")) {
                                    showDialog("", getString(R.string.error_role_login));
                                } else {
                                    showDialog("", data.getMsg(), (dialogInterface, i) -> {
                                        if (data.getMsg().contains("verify")) {
                                            resendOTP();
                                        } else if (data.getMsg().contains("mail_verify")) {
                                            mListener.validateMailOTP();
                                        } else if (data.getMsg().contains("pay")) {
                                            mListener.proceedToPayment(data.getPaymentData());
                                        }
                                    });
                                }
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        String errorMessage = response.message();
                        if (!TextUtils.isEmpty(errorMessage)) {
                            showDialog(errorMessage);
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void resendOTP() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            authenticationService.resendOTP(mMobileNumber, Utils.CLIENT_ID).enqueue(new Callback<ResendOTPResponse>() {
                @Override
                public void onResponse(@NotNull Call<ResendOTPResponse> call, @NotNull Response<ResendOTPResponse> response) {
                    if (response.isSuccessful()) {
                        ResendOTPResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals("Success")) {
                                showDialog("", data.getMsg() + " OTP: " + data.getOtp(), ((dialogInterface, i) -> mListener.validateOTP(mMobileNumber, false)));
                            } else {
                                showDialog("", data.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showDialog("", response.message());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<ResendOTPResponse> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void checkRegistration(ProfileResponse profileResponse, String mMobileNumber, String mPassword) {
        if (Utils.isNetworkConnected(getContext())) {

                ProgressDialog progressBar = new ProgressDialog(getActivity(), R.style.mySpinnerTheme);
                progressBar.setCancelable(false);
                progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressBar.show();

                AuthenticationService getUserDataService = RetrofitClientInstance.getInstance().getRetrofitInstanceRokad().create(AuthenticationService.class);
                Call<BaseResponse> user = getUserDataService.registrationRokad(profileResponse.getFirstName(),profileResponse.getLastName(),mMobileNumber,profileResponse.getEmail(), mPassword,mPassword,profileResponse.getUserID());//("", "");
                user.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        Log.d("onResponse", "onResponse: Login Fragment");
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                            showDialog("success", response.body().getMsg());

                        } else if (response.body() != null){
                            showDialog("Sorry..", response.body().getMsg());
                        } else {
                            showDialog("Sorry..","Looks like server is not available at the moment. Please make sure your Internet Connectivity is stable" +
                                    " and try again after some time.");
                        }
                        //progressBar.dismiss();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        // Log.d("onFailure", "onFailure: Login Fragment ");
                        progressBar.dismiss();
                        if(t instanceof SocketTimeoutException){
                            showDialog(getString(R.string.time_out_title), getString(R.string.time_out_msg));
                        } else {
                            showDialog("Sorry..!!", getString(R.string.server_failed_case));
//                            Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        // showDialog("Sorry..", getString(R.string.internet_failed_login_case));
                    }
                });


        } else {
            showDialog("Sorry!!", getString(R.string.internet_check));
        }
    }

}