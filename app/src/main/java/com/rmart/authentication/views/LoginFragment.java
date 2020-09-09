package com.rmart.authentication.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.rmart.R;
import com.rmart.baseclass.views.CustomEditTextWithErrorText;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.pojos.ProfileResponse;
import com.rmart.utilits.pojos.ResendOTPResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends LoginBaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String mMobileNumber;

    private String mPassword;
    private AppCompatEditText etPassword, etMobileNumber;
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
        etMobileNumber = ((CustomEditTextWithErrorText)view.findViewById(R.id.mobile_number)).getAppCompatEditText();
        etPassword = ((CustomEditTextWithErrorText)view.findViewById(R.id.password)).getAppCompatEditText();
        view.findViewById(R.id.login).setOnClickListener(this);
        view.findViewById(R.id.register).setOnClickListener(this);
        view.findViewById(R.id.forgot_password).setOnClickListener(this);

        etMobileNumber.setText("7416226233");
        etPassword.setText("1234");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login) {
            mMobileNumber = Objects.requireNonNull(etMobileNumber.getText()).toString().trim();
            mPassword = Objects.requireNonNull(etPassword.getText()).toString();
            if (TextUtils.isEmpty(mMobileNumber) || !Utils.isValidMobile(mMobileNumber)) {
                showDialog("", getString(R.string.error_mobile));
            } else if (TextUtils.isEmpty(mPassword)) {
                showDialog("", getString(R.string.error_password));
            } else {
                checkCredentials();
            }
        } else if (view.getId() == R.id.forgot_password) {
            mListener.goToForgotPassword();
        } else  {
            mListener.goToRegistration();
        }
    }

    private void checkCredentials() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            // mPassword = "12345";
            String mDeviceKey = "deviceKey";
            authenticationService.login(mDeviceKey, mMobileNumber, mPassword).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                if (data.getLoginData().getRoleID().equalsIgnoreCase(getString(R.string.role_id))) {
                                    try {
                                        ProfileResponse profileResponse = data.getLoginData();
                                        MyProfile.setInstance(profileResponse);
                                        if (MyProfile.getInstance().getPrimaryAddressId() == null) {
                                            mListener.goToProfileActivity();
                                        } else {
                                            switch (data.getLoginData().getRoleID()) {
                                                case Utils.CUSTOMER_ID:
                                                    mListener.goToCustomerHomeActivity();
                                                    SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.putString(getString(R.string.uid), Utils.CUSTOMER_ID);
                                                    editor.apply();
                                                    break;
                                                case Utils.RETAILER_ID:
                                                    mListener.goToHomeActivity();
                                                    break;
                                                case Utils.DELIVERY_ID:
                                                    break;
                                            }
                                        }
                                        // mListener.goToProfileActivity();
                                        Objects.requireNonNull(getActivity()).onBackPressed();
                                    } catch (Exception e) {
                                        showDialog(e.getMessage());
                                    }
                                } else {
                                    showDialog("", getString(R.string.error_role_login));
                                }
                            } else {
                                showDialog("", data.getMsg(), (dialogInterface, i) -> {
                                    if (data.getMsg().contains("verify")) {
                                        resendOTP();
                                    } else if (data.getMsg().contains("mail_verify")) {
                                        mListener.validateMailOTP();
                                    }
                                });
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    showDialog("", t.getMessage());
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
            authenticationService.resendOTP(mMobileNumber).enqueue(new Callback<ResendOTPResponse>() {
                @Override
                public void onResponse(@NotNull Call<ResendOTPResponse> call, @NotNull Response<ResendOTPResponse> response) {
                    if (response.isSuccessful()) {
                        ResendOTPResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equals("Success")) {
                                showDialog("", data.getMsg() + " OTP: " + data.getOtp(), ((dialogInterface, i) -> mListener.validateOTP(mMobileNumber)));
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

                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }
}