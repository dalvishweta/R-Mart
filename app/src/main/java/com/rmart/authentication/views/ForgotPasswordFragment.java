package com.rmart.authentication.views;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rmart.R;
import com.rmart.authentication.OnAuthenticationClickedListener;
import com.rmart.baseclass.views.CustomEditTextWithErrorText;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ForgotPasswordResponse;
import com.rmart.utilits.services.AuthenticationService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends LoginBaseFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    OnAuthenticationClickedListener mListener;
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context ;
    }

    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatEditText etMobileNumber = ((CustomEditTextWithErrorText) view.findViewById(R.id.mobile_number)).getAppCompatEditText();
        view.findViewById(R.id.forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mMobileNumber = Objects.requireNonNull(etMobileNumber.getText()).toString();
                if(mMobileNumber.length()<=0 || !Utils.isValidMobile(mMobileNumber)) {
                    showDialog("", getString(R.string.error_mobile));
                } else {
                    progressDialog.show();
                    AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
                    authenticationService.forgotPassword(mMobileNumber).enqueue(new Callback<ForgotPasswordResponse>() {
                        @Override
                        public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                            if(response.isSuccessful()) {
                                ForgotPasswordResponse data = response.body();
                                assert data != null;
                                if(data.getStatus().equalsIgnoreCase("Success")) {
                                   showDialog("", data.getMsg()+" otp: "+ data.getOtp(), (dialog,i)-> {
                                       mListener.changePassword("otp", mMobileNumber);
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
                        public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            showDialog("", t.getMessage());
                        }
                    });
                }
            }
        });
        
    }
}