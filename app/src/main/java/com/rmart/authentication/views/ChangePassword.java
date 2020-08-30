package com.rmart.authentication.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.CustomEditTextWithErrorText;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.pojos.ChangePasswordResponse;
import com.rmart.utilits.services.AuthenticationService;

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
        if(mOTP.length()>0) {
            Objects.requireNonNull(getActivity()).setTitle(R.string.forgot_password);
        } else {
            Objects.requireNonNull(getActivity()).setTitle(R.string.change_password);
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
        AppCompatEditText firstField = ((CustomEditTextWithErrorText) view.findViewById(R.id.current_password)).getAppCompatEditText();
        if(mOTP.length()>0) {
            firstField.setHint(R.string.otp);
        } else {
            firstField.setHint(R.string.hint_current_password);
        }
        AppCompatEditText password = ((CustomEditTextWithErrorText) view.findViewById(R.id.password)).getAppCompatEditText();
        AppCompatEditText confirmPassword = ((CustomEditTextWithErrorText) view.findViewById(R.id.confirm_password)).getAppCompatEditText();

        view.findViewById(R.id.register).setOnClickListener(view1 -> {
            String _password = Objects.requireNonNull(password.getText()).toString();
            String _confirmPassword = Objects.requireNonNull(confirmPassword.getText()).toString();
            String otp = Objects.requireNonNull(firstField.getText()).toString();
            if(otp.length()<=0) {
                showDialog("", getString(R.string.error_otp));
            } else if(_password.length()<=0) {
                showDialog("", getString(R.string.error_empty_password));
            } else if(_confirmPassword.length() <= 0) {
                showDialog("", getString(R.string.error_empty_confirm_password));
            } else if (!_password.equals(_confirmPassword)) {
                showDialog("", getString(R.string.mismatch_confirm_password));
            } else {
                progressDialog.show();
                AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
                if(mOTP.length()>0) {
                    authenticationService.changePasswordOTP(mMobileNumber, otp, _password).enqueue(new Callback<ChangePasswordResponse>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                            if(response.isSuccessful()) {
                                ChangePasswordResponse data = response.body();
                                if(data.getStatus().contains("Success")) {
                                    showDialog("", data.getMsg(), (dialog, i) -> {
                                                mListener.goToHomePage();
                                    });
                                } else {
                                    showDialog("", data.getMsg());
                                }
                            } else {
                                showDialog("", response.message());
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                            showDialog("", t.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    authenticationService.changePassword(mMobileNumber, otp, _password).enqueue(new Callback<ChangePasswordResponse>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                            if(response.isSuccessful()) {
                                ChangePasswordResponse data = response.body();
                                if(data.getStatus().contains("Success")) {
                                    showDialog("", data.getMsg(), (dialog, i) -> {
                                        mListener.goToHomePage();
                                    });
                                } else {
                                    showDialog("", data.getMsg());
                                }
                            } else {
                                showDialog("", response.message());
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                            showDialog("", t.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });

    }
}