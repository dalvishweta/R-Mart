package com.rmart.authentication.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.rmart.R;
import com.rmart.authentication.OnAuthenticationClickedListener;
import com.rmart.baseclass.views.CustomEditTextWithErrorText;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ForgotPasswordResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends LoginBaseFragment {

    private OnAuthenticationClickedListener mListener;
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context ;
    }

    public static ForgotPasswordFragment getInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatEditText etMobileNumber = view.findViewById(R.id.mobile_number);
        view.findViewById(R.id.forgot_password).setOnClickListener(view1 -> {
            String mMobileNumber = Objects.requireNonNull(etMobileNumber.getText()).toString().trim();
            if (mMobileNumber.length() == 0 || !Utils.isValidMobile(mMobileNumber)) {
                showDialog("", getString(R.string.error_mobile));
            } else {
                progressDialog.show();
                AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
                authenticationService.forgotPassword(mMobileNumber).enqueue(new Callback<ForgotPasswordResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ForgotPasswordResponse> call, @NotNull Response<ForgotPasswordResponse> response) {
                        if (response.isSuccessful()) {
                            ForgotPasswordResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().equalsIgnoreCase("Success")) {
                                    showDialog("", data.getMsg() + " otp: " + data.getOtp(), (dialog, i) -> {
                                        mListener.changePassword("otp", mMobileNumber);
                                    });
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
                    public void onFailure(@NotNull Call<ForgotPasswordResponse> call, @NotNull Throwable t) {
                        progressDialog.dismiss();
                        showDialog("", t.getMessage());
                    }
                });
            }
        });
        
    }
}