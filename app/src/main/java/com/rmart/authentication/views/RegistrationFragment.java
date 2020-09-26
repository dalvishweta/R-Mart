package com.rmart.authentication.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.RegistrationResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends LoginBaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    AppCompatEditText tvFullName, tvLastName, tVMobileNumber, tvEmail, tvPassword, tvConformPassword;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.register).setOnClickListener(this);
        tvFullName = view.findViewById(R.id.full_name);
        tvLastName = view.findViewById(R.id.lase_name);
        tVMobileNumber = view.findViewById(R.id.mobile_number);
        tvEmail = view.findViewById(R.id.email);
        tvPassword = view.findViewById(R.id.password);
        tvConformPassword = view.findViewById(R.id.confirm_password);
    }

    @Override
    public void onClick(View view) {
        validateRegistration();
        // mListener.validateOTP();

    }

    private void validateRegistration() {
        String firstName, lastName, mobileNumber, email, password, conformPassword;
        firstName = Objects.requireNonNull(tvFullName.getText()).toString().trim();
        lastName = Objects.requireNonNull(tvLastName.getText()).toString().trim();
        mobileNumber = Objects.requireNonNull(tVMobileNumber.getText()).toString().trim();
        email = Objects.requireNonNull(tvEmail.getText()).toString().trim();
        password = Objects.requireNonNull(tvPassword.getText()).toString().trim();
        conformPassword = Objects.requireNonNull(tvConformPassword.getText()).toString().trim();

        /*firstName = "ffff";
        lastName = "lllll";
        mobileNumber = "1234556";
        email = "v@v.com";
        password= "1234";
        conformPassword = "1234";*/

        if (firstName.length() <= 2) {
            showDialog("", getString(R.string.error_full_name));
        } else if (lastName.length() <= 2) {
            showDialog("", getString(R.string.error_last_name));
        } else if (mobileNumber.length() <= 2) {
            showDialog("", getString(R.string.required_mobile_number));
        } else if (!Utils.isValidMobile(mobileNumber)) {
            showDialog("", getString(R.string.error_mobile_number));
        } else if (email.length() <= 2) {
            showDialog("", getString(R.string.required_mail));
        } else if (!Utils.isValidEmail(email)) {
            showDialog("", getString(R.string.error_mail));
        } else if (password.length() <= 2) {
            showDialog("", getString(R.string.error_empty_password));
        } else if (conformPassword.length() <= 2) {
            showDialog("", getString(R.string.error_empty_confirm_password));
        } else if (!conformPassword.equals(password)) {
            showDialog("", getString(R.string.mismatch_confirm_password));
        } else {
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            authenticationService.registration(firstName, lastName, mobileNumber, email, password, getString(R.string.role_id), Utils.CLIENT_ID).enqueue(
                    new Callback<RegistrationResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                            if (response.isSuccessful()) {
                                RegistrationResponse data = response.body();
                                if (data != null) {
                                    if (data.getStatus().equals("Success")) {
                                        showDialog("", data.getMsg() + " OTP: " + data.getOtp(), (click, i) -> {
                                            resetFields();
                                            mListener.validateOTP(mobileNumber);
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
                        public void onFailure(@NotNull Call<RegistrationResponse> call, @NotNull Throwable t) {
                            showDialog("", t.getMessage());
                            progressDialog.dismiss();
                        }
                    }
            );
        }
    }

    private void resetFields() {
        tvFullName.setText("");
        tvLastName.setText("");
        tVMobileNumber.setText("");
        tvEmail.setText("");
        tvPassword.setText("");
        tvConformPassword.setText("");
    }
}