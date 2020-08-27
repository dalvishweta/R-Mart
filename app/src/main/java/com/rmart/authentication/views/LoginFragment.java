package com.rmart.authentication.views;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rmart.R;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstanceOld;
import com.rmart.utilits.pojos.LoginResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends LoginBaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login).setOnClickListener(this);
        view.findViewById(R.id.register).setOnClickListener(this);
        view.findViewById(R.id.forgot_password).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login) {
            // mListener.validateMailOTP();
            // checkCredentials();
            MyProfile.getInstance();
            if (MyProfile.getInstance().getMyLocations() == null || MyProfile.getInstance().getMyLocations().size() <= 0) {
                mListener.goToProfileActivity();
            } else {
                mListener.goToHomeActivity();
            }
        } else if (view.getId() == R.id.forgot_password) {
            mListener.goToForgotPassword();
        } else  {
            mListener.goToRegistration();
        }
    }

    private void checkCredentials() {
        progressDialog.show();
        AuthenticationService authenticationService = RetrofitClientInstanceOld.getRetrofitInstance().create(AuthenticationService.class);
        authenticationService.login("deviceKey", "7416226233", "12345").enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse data = response.body();
                    if (data != null) {
                        if (data.getStatus().equalsIgnoreCase("success")) {
                            try {
                                MyProfile.getInstance(data.getData().getProfileModel());
                                if (MyProfile.getInstance().getMyLocations() == null || MyProfile.getInstance().getMyLocations().size() <= 0) {
                                    mListener.goToProfileActivity();
                                } else {
                                    mListener.goToHomeActivity();
                                }
                                Objects.requireNonNull(getActivity()).onBackPressed();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            showDialog("", data.getMsg(), (dialogInterface, i) -> {
                                if (data.getMsg().contains("verify")) {
                                    mListener.validateOTP();
                                } else if (data.getMsg().contains("mail_verify")) {
                                    mListener.validateMailOTP();
                                }
                            });
                        }
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
    }
}