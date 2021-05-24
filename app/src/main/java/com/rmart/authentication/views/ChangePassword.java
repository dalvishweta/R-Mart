package com.rmart.authentication.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ChangePasswordResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends LoginBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mOTP,firstField,passwordField,confirmPasswordField;
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

        //TextInputLayout firstFieldLayout = view.findViewById(R.id.current_password_layout);

        try{
            ImageView slice = view.findViewById(R.id.slice);
            ImageView  app_logo = view.findViewById(R.id.app_logo);

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
       /* if (TextUtils.isEmpty(mOTP)) {
            firstFieldLayout.setHint(R.string.hint_current_password);
        } else {
            firstFieldLayout.setHint(R.string.otp);
        }*/
        //AppCompatEditText passwordField = view.findViewById(R.id.password);
        AppCompatEditText editTextOne = view.findViewById(R.id.editTextOne);
        AppCompatEditText editTextTwo = view.findViewById(R.id.editTextTwo);
        AppCompatEditText editTextThree = view.findViewById(R.id.editTextThree);
        AppCompatEditText editTextFour = view.findViewById(R.id.editTextFour);
        AppCompatEditText confirm_editTextOne = view.findViewById(R.id.confirm_editTextOne);
        AppCompatEditText confirm_editTextTwo = view.findViewById(R.id.confirm_editTextTwo);
        AppCompatEditText confirm_editTextThree = view.findViewById(R.id.confirm_editTextThree);
        AppCompatEditText confirm_editTextFour = view.findViewById(R.id.confirm_editTextFour);
        AppCompatEditText otp_editTextOne = view.findViewById(R.id.otp_editTextOne);
        AppCompatEditText otp_editTextTwo = view.findViewById(R.id.otp_editTextTwo);
        AppCompatEditText otp_editTextThree = view.findViewById(R.id.otp_editTextThree);
        AppCompatEditText otp_editTextFour = view.findViewById(R.id.otp_editTextFour);
        //AppCompatEditText confirmPasswordField = view.findViewById(R.id.confirm_password);
        String passwordField = editTextOne.getText().toString().trim()+editTextTwo.getText().toString().trim()+editTextThree.getText().toString().trim()+editTextFour.getText().toString().trim();
        Log.d("PASS",passwordField+" ");
        view.findViewById(R.id.register).setOnClickListener(v -> {
            String password = editTextOne.getText().toString().trim()+editTextTwo.getText().toString().trim()+editTextThree.getText().toString().trim()+editTextFour.getText().toString().trim();
            Log.d("PASSWORD",password+" ");
            String confirmPassword = Objects.requireNonNull(confirm_editTextOne.getText().toString().trim()+confirm_editTextTwo.getText().toString().trim()+confirm_editTextThree.getText().toString().trim()+confirm_editTextFour.getText().toString().trim());
            Log.d("CPASSWORD",confirmPassword+" ");
            String otp = Objects.requireNonNull(otp_editTextOne.getText().toString().trim()+otp_editTextTwo.getText().toString().trim()+otp_editTextThree.getText().toString().trim()+otp_editTextFour.getText().toString().trim());
            Log.d("oTpPASSWORD",otp+" ");

            //String passwordError = Utils.isValidPassword(password);

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
           /* if (passwordError.length()>0) {
                showDialog("", passwordError);
                return;
            }*/
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
                authenticationService.changePasswordOTP(mMobileNumber, otp, password, BuildConfig.ROLE_ID).enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ChangePasswordResponse> call, @NotNull Response<ChangePasswordResponse> response) {
                        if (response.isSuccessful()) {
                            ChangePasswordResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().contains("Success")) {
                                    showDialog("", data.getMsg(), (dialog, i) -> mListener.goToHomePage());
                                } else {
                                    if (data.getMsg().contains("role id")) {
                                        showDialog("", getString(R.string.error_role_login));
                                    } else {
                                        showDialog(data.getMsg());
                                    }
                                    // showDialog(data.getMsg());
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
                        if(t instanceof SocketTimeoutException){
                            showDialog("", getString(R.string.network_slow));
                        } else {
                            showDialog("", t.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                });
            } else {
                authenticationService.changePassword(mMobileNumber, otp, password, BuildConfig.ROLE_ID).enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ChangePasswordResponse> call, @NotNull Response<ChangePasswordResponse> response) {
                        if (response.isSuccessful()) {
                            ChangePasswordResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus().contains("Success")) {
                                    showDialog("", data.getMsg(), (dialog, i) -> mListener.goToHomePage());
                                } else {
                                    if (data.getMsg().contains("role id")) {
                                        showDialog("", getString(R.string.error_role_login));
                                    } else {
                                        showDialog(data.getMsg());
                                    }
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
                        if(t instanceof SocketTimeoutException){
                            showDialog("", getString(R.string.network_slow));
                        } else {
                            showDialog("", t.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
}