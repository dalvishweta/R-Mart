package com.rmart.authentication.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rmart.R;
import com.rmart.baseclass.views.CustomEditTextWithErrorText;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.RegistrationResponse;
import com.rmart.utilits.services.AuthenticationService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends LoginBaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    CustomEditTextWithErrorText tvFullName, tvLastName, tVMobileNumber, tvEmail, tvPassword, tvConformPassword;
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
        firstName = Objects.requireNonNull(tvFullName.getAppCompatEditText().getText()).toString();
        lastName = Objects.requireNonNull(tvLastName.getAppCompatEditText().getText()).toString();
        mobileNumber = Objects.requireNonNull(tVMobileNumber.getAppCompatEditText().getText()).toString();
        email = Objects.requireNonNull(tvEmail.getAppCompatEditText().getText()).toString();
        password = Objects.requireNonNull(tvPassword.getAppCompatEditText().getText()).toString();
        conformPassword = Objects.requireNonNull(tvConformPassword.getAppCompatEditText().getText()).toString();

        /*firstName = "ffff";
        lastName = "lllll";
        mobileNumber = "1234556";
        email = "v@v.com";
        password= "1234";
        conformPassword = "1234";*/

        if(firstName.length()<=2) {
            showDialog("", getString(R.string.error_full_name));
        } else if(lastName.length()<=2) {
            showDialog("", getString(R.string.error_mobile_number));
        } else if(mobileNumber.length()<=2) {
            showDialog("", getString(R.string.required_mobile_number));
        } else if(!Utils.isValidMobile(mobileNumber)) {
            showDialog("", getString(R.string.error_mobile_number));
        } else if(email.length()<=2) {
            showDialog("", getString(R.string.required_mail));
        } else if(!Utils.isValidEmail(email)) {
            showDialog("", getString(R.string.error_mail));
        } else if(password.length()<=2) {
            showDialog("", getString(R.string.error_empty_password));
        } else if(conformPassword.length()<=2) {
            showDialog("", getString(R.string.error_empty_confirm_password));
        } else if(!conformPassword.equals(password)) {
            showDialog("", getString(R.string.mismatch_confirm_password));
        } else {
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            authenticationService.registration(firstName, lastName, mobileNumber, email, password, getString(R.string.role_id), Utils.CLIENT_ID).enqueue(
                    new Callback<RegistrationResponse>() {
                        @Override
                        public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                            if(response.isSuccessful()) {
                                RegistrationResponse date = response.body();
                                assert date != null;
                                if(date.getStatus().equals("Success")) {
                                    showDialog("", date.getMsg()+" OTP: "+date.getOtp(),(click, i)-> {
                                        mListener.validateOTP(mobileNumber);
                                    });
                                } else {
                                    showDialog("", date.getMsg());
                                }
                            } else {
                                showDialog("", response.message());
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                            showDialog("", t.getMessage());
                            progressDialog.dismiss();
                        }
                    }
            );
           /* authenticationService.getREGISTRATION().enqueue(new Callback<List<BaseResponse>>() {
                @Override
                public void onResponse(Call<List<BaseResponse>> call, Response<List<BaseResponse>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        showDialog("", response.body().toString());
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<BaseResponse>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });*/

            /*authenticationService.getProducts().enqueue(new Callback<List<ProductPojo>>() {
                @Override
                public void onResponse(Call<List<ProductPojo>> call, Response<List<ProductPojo>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        showDialog("", response.body().toString());
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<ProductPojo>> call, Throwable t) {
                    progressDialog.dismiss();
                }

            });*/

            /*progressDialog.show();
            authenticationService.validateOTP("111332333", "3519").enqueue(new Callback<ValidateOTP>() {
                @Override
                public void onResponse(Call<ValidateOTP> call, Response<ValidateOTP> response) {
                    if (response.isSuccessful()) {
                        showDialog("", response.body().getMsg());
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<ValidateOTP> call, Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });*/
        }
    }
}