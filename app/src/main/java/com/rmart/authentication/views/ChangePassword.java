package com.rmart.authentication.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.rmart.R;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ChangePasswordResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends LoginBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mOTP;
    private String mMobileNumber;

    public ChangePassword() {
        // Required empty public constructor
    }

    public static ChangePassword newInstance(String param1, String param2) {
        ChangePassword fragment = new ChangePassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOTP = getArguments().getString(ARG_PARAM1);
            mMobileNumber = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mOTP.length() > 0) {
            Objects.requireNonNull(requireActivity()).setTitle(R.string.forgot_password);
        } else {
            Objects.requireNonNull(requireActivity()).setTitle(R.string.change_password);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatEditText firstField = view.findViewById(R.id.current_password);
        if (TextUtils.isEmpty(mOTP)) {
            firstField.setHint(R.string.hint_current_password);
        } else {
            firstField.setHint(R.string.otp);
        }
        AppCompatEditText passwordField = view.findViewById(R.id.password);
        AppCompatEditText confirmPasswordField = view.findViewById(R.id.confirm_password);

        view.findViewById(R.id.register).setOnClickListener(v -> {
            String password = Objects.requireNonNull(passwordField.getText()).toString().trim();
            String confirmPassword = Objects.requireNonNull(confirmPasswordField.getText()).toString().trim();
            String otp = Objects.requireNonNull(firstField.getText()).toString().trim();

            String passwordError = Utils.isValidPassword(password);

            if (TextUtils.isEmpty(otp)) {
                if (TextUtils.isEmpty(mOTP)) {
                    showDialog("", getString(R.string.error_empty_password));
                } else {
                    showDialog(getString(R.string.error_otp));
                }
                return;
            }

            if (TextUtils.isEmpty(password) || password.length() <= 0) {
                showDialog("", getString(R.string.error_empty_password));
                return;
            }
            if (passwordError.length()>0) {
                showDialog("", passwordError);
                return;
            }
            /*if (TextUtils.isEmpty(password) || password.length() < Utils.MIN_PASSWORD_LENGTH) {
                showDialog(getString(R.string.password_strength_error));
                return;
            }
            if (TextUtils.isEmpty(confirmPassword) || confirmPassword.length() < Utils.MIN_PASSWORD_LENGTH) {
                showDialog(getString(R.string.confirm_password_strength_error));
                return;
            }*/
            if (!password.equals(confirmPassword)) {
                showDialog(getString(R.string.mismatch_confirm_password));
                return;
            }
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }

            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            if (mOTP.length() > 0) {
                authenticationService.changePasswordOTP(mMobileNumber, otp, password).enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ChangePasswordResponse> call, @NotNull Response<ChangePasswordResponse> response) {
                        if (response.isSuccessful()) {
                            ChangePasswordResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().contains("Success")) {
                                    showDialog("", data.getMsg(), (dialog, i) -> mListener.goToHomePage());
                                } else {
                                    showDialog(data.getMsg());
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                        } else {
                            showDialog(response.message());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<ChangePasswordResponse> call, @NotNull Throwable t) {
                        showDialog("", t.getMessage());
                        progressDialog.dismiss();
                    }
                });
            } else {
                authenticationService.changePassword(mMobileNumber, otp, password).enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ChangePasswordResponse> call, @NotNull Response<ChangePasswordResponse> response) {
                        if (response.isSuccessful()) {
                            ChangePasswordResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().contains("Success")) {
                                    showDialog("", data.getMsg(), (dialog, i) -> mListener.goToHomePage());
                                } else {
                                    showDialog(data.getMsg());
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                        } else {
                            showDialog(response.message());
                        }
                        progressDialog.dismiss();
                    }

                        @Override
                        public void onFailure(@NotNull Call<ChangePasswordResponse> call, @NotNull Throwable t) {
                            showDialog("", t.getMessage());
                            progressDialog.dismiss();
                        }
                });
            }
        });
    }
}