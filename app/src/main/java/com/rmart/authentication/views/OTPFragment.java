package com.rmart.authentication.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmart.R;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ResendOTPResponse;
import com.rmart.utilits.pojos.ValidateOTP;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPFragment extends LoginBaseFragment implements TextWatcher {

    private static final String ARG_PARAM1 = "param1";
    public static final int INT_OTP_LENGTH = 4;
    AppCompatEditText otpEditText;
    EditText editTextOne,editTextTwo,editTextThree,editTextFour;
    // private String mParam1;
    private String mParam2;
    private String mMobileNumber;

    public OTPFragment() {
        // Required empty public constructor
    }

    public static OTPFragment newInstance(String mobileNumber) {
        OTPFragment fragment = new OTPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mobileNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMobileNumber = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_t_p, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            slice.requestLayout();
            app_logo.requestLayout();



        } catch (Exception e){



        }
        editTextOne = view.findViewById(R.id.editTextOne);
        editTextTwo = view.findViewById(R.id.editTextTwo);
        editTextThree = view.findViewById(R.id.editTextThree);
        editTextFour = view.findViewById(R.id.editTextFour);

        //otpEditText = view.findViewById(R.id.otp);
        editTextOne.addTextChangedListener(this);
        editTextTwo.addTextChangedListener(this);
        editTextThree.addTextChangedListener(this);
        editTextFour.addTextChangedListener(this);
        view.findViewById(R.id.resend).setOnClickListener(v -> resendOTP());
        ((TextView) view.findViewById(R.id.otp_mobile_sent)).setText(String.format(getString(R.string.verification_code_mobile_hint), mMobileNumber));
    }

    private void resendOTP() {
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            return;
        }
        AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
        authenticationService.resendOTP(mMobileNumber, Utils.CLIENT_ID).enqueue(new Callback<ResendOTPResponse>() {
            @Override
            public void onResponse(@NotNull Call<ResendOTPResponse> call, @NotNull Response<ResendOTPResponse> response) {
                if (response.isSuccessful()) {
                    ResendOTPResponse data = response.body();
                    if(data != null) {
                        if (data.getStatus().equals("Success")) {
                            String text = data.getMsg();
                            if(null != data.getOtp()) {
                                text = text+", OTP: " +data.getOtp();
                            }
                            showDialog(text);//getString(R.string.payment_success_message));
                        } else {
                            showDialog("", data.getMsg());
                        }
                    }
                } else {
                    showDialog("", response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ResendOTPResponse> call, @NotNull Throwable t) {
               try {
                   if (t instanceof SocketTimeoutException) {
                       showDialog("", getString(R.string.network_slow));
                   } else {
                       showDialog("", t.getMessage());
                   }
                   progressDialog.dismiss();
               }catch (Exception e){

               }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("DATA",s.toString());
        String otp_data = editTextOne.getText().toString().trim()+editTextTwo.getText().toString().trim()+editTextThree.getText().toString().trim()+editTextFour.getText().toString().trim();
        Log.d("data1",otp_data);
        if (otp_data.length() == INT_OTP_LENGTH) {
            if(!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            Log.d("DATA123",otp_data);
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            authenticationService.validateOTP(mMobileNumber, otp_data).enqueue(new Callback<ValidateOTP>() {
                @Override
                public void onResponse(@NotNull Call<ValidateOTP> call, @NotNull Response<ValidateOTP> response) {
                    if (response.isSuccessful()) {
                        ValidateOTP data = response.body();
                        if(data != null) {
                            if (data.getStatus().equalsIgnoreCase("Success")) {
                                showDialog("", "Your account is activated please login.", (dialog, i) -> mListener.goToHomePage());
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
                public void onFailure(@NotNull Call<ValidateOTP> call, @NotNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        showDialog("", getString(R.string.network_slow));
                    } else {
                        showDialog("", t.getMessage());
                    }
                    progressDialog.dismiss();
                }
            });
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}