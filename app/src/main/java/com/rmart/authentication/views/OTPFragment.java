package com.rmart.authentication.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rmart.R;
import com.rmart.baseclass.views.CustomEditTextWithErrorText;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.pojos.ValidateOTP;
import com.rmart.utilits.services.AuthenticationService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPFragment extends LoginBaseFragment implements TextWatcher {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int INT_OTP_LENGTH = 4;
    CustomEditTextWithErrorText otpEditText;
    // private String mParam1;
    private String mParam2;
    private String mMobileNumber;

    public OTPFragment() {
        // Required empty public constructor
    }

    public static OTPFragment newInstance(String param1, String param2) {
        OTPFragment fragment = new OTPFragment();
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
            mMobileNumber = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        otpEditText = view.findViewById(R.id.otp);
        otpEditText.getAppCompatEditText().addTextChangedListener(this);
        ((TextView)view.findViewById(R.id.otp_mobile_sent)).setText(String.format(getString(R.string.verification_code_mobile_hint), MyProfile.getInstance().getMobileNumber()));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().length() >= INT_OTP_LENGTH) {
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            authenticationService.validateOTP(mMobileNumber, s.toString()).enqueue(new Callback<ValidateOTP>() {
                @Override
                public void onResponse(Call<ValidateOTP> call, Response<ValidateOTP> response) {
                    if(response.isSuccessful()) {
                        ValidateOTP date = response.body();
                        assert date != null;
                        if(date.getStatus().equalsIgnoreCase("Succes")) {
                            showDialog("", "Your account is activated please login.", (dialog, i) -> {
                                mListener.goToHomePage();
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
                public void onFailure(Call<ValidateOTP> call, Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
            Objects.requireNonNull(getActivity()).finish();

            mListener.goToHomeActivity();
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}