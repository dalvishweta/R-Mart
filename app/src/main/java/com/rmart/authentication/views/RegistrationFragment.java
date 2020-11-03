package com.rmart.authentication.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.customer.adapters.CustomSpinnerAdapter;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.RegistrationFeeStructure;
import com.rmart.utilits.pojos.RegistrationResponse;
import com.rmart.utilits.services.AuthenticationService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends LoginBaseFragment implements View.OnClickListener {

    private AppCompatEditText tvFirstName, tvLastName, tVMobileNumber, tvEmail, tvPassword, tvConformPassword;
    private String selectedGender;

    ArrayList<RegistrationFeeStructure> registrationFeeStructuresList = new ArrayList<>();

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment getInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getFeeDetails();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    private void getFeeDetails() {
        registrationFeeStructuresList.clear();
        RegistrationFeeStructure registration = new RegistrationFeeStructure();
        registration.setPayType(getString(R.string.registration_fee));
        registration.setAmount("2000");

        RegistrationFeeStructure gst = new RegistrationFeeStructure();
        gst.setPayType("GST 18%");
        gst.setAmount("360");

        RegistrationFeeStructure total = new RegistrationFeeStructure();
        total.setPayType(getString(R.string.total_amount));
        total.setAmount("2360");

        registrationFeeStructuresList.add(registration);
        registrationFeeStructuresList.add(gst);
        registrationFeeStructuresList.add(total);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button register = view.findViewById(R.id.register);
        register.setOnClickListener(this);
        tvFirstName = view.findViewById(R.id.first_name);
        tvLastName = view.findViewById(R.id.lase_name);
        tVMobileNumber = view.findViewById(R.id.mobile_number);
        tvEmail = view.findViewById(R.id.email);
        tvPassword = view.findViewById(R.id.password);
        tvConformPassword = view.findViewById(R.id.confirm_password);
        AppCompatSpinner genderSpinnerField = view.findViewById(R.id.gender_spinner_field);

        List<Object> gendersList = new ArrayList<>();

        gendersList.add(Utils.SELECT_YOUR_GENDER);
        gendersList.add("Male");
        gendersList.add("Female");
        gendersList.add("Other");

        genderSpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                selectedGender = (String) gendersList.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomSpinnerAdapter customStringAdapter = new CustomSpinnerAdapter(requireActivity(), gendersList);
        genderSpinnerField.setAdapter(customStringAdapter);

        LinearLayout paymentBase = view.findViewById(R.id.payment_base);
        paymentBase.removeAllViews();
        if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            paymentBase.removeAllViews();
            paymentBase.setVisibility(View.VISIBLE);
            for (RegistrationFeeStructure feeStructure :
                    registrationFeeStructuresList) {
                View v = View.inflate(requireActivity(), R.layout.layout, null);
                ((AppCompatTextView) v.findViewById(R.id.pay_type)).setText(feeStructure.getPayType());
                ((AppCompatTextView) v.findViewById(R.id.pay_amt)).setText(feeStructure.getAmount());
                paymentBase.addView(v);
            }
            register.setText(R.string.proceed_to_pay);
        } else {
            view.findViewById(R.id.tv_payment_information_field).setVisibility(View.GONE);
            paymentBase.setVisibility(View.GONE);
            register.setText(R.string.register_right);
        }
    }

    @Override
    public void onClick(View view) {
        validateRegistration();
        // mListener.validateOTP();

    }

    private void validateRegistration() {
        String firstName, lastName, mobileNumber, email, password, confirmPassword;
        firstName = Objects.requireNonNull(tvFirstName.getText()).toString().trim();
        lastName = Objects.requireNonNull(tvLastName.getText()).toString().trim();
        mobileNumber = Objects.requireNonNull(tVMobileNumber.getText()).toString().trim();
        email = Objects.requireNonNull(tvEmail.getText()).toString().trim();
        password = Objects.requireNonNull(tvPassword.getText()).toString().trim();
        confirmPassword = Objects.requireNonNull(tvConformPassword.getText()).toString().trim();
        String passwordError = Utils.isValidPassword(password);
        String confirmPasswordError = Utils.isValidPassword(confirmPassword);
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
        } else if (TextUtils.isEmpty(selectedGender) || selectedGender.equalsIgnoreCase(Utils.SELECT_YOUR_GENDER)) {
            showDialog(getString(R.string.required_gender));
        } else if (mobileNumber.length() <= 2) {
            showDialog("", getString(R.string.required_mobile_number));
        } else if (!Utils.isValidMobile(mobileNumber)) {
            showDialog("", getString(R.string.error_mobile_number));
        } else if (email.length() <= 0) {
            showDialog("", getString(R.string.required_mail));
        } else if (!Utils.isValidEmail(email)) {
            showDialog("", getString(R.string.error_mail));
        } else if (TextUtils.isEmpty(password)) {
            showDialog("", getString(R.string.error_empty_password));
        } else if (!TextUtils.isEmpty(passwordError)) {
            showDialog("", passwordError);
        } else if (TextUtils.isEmpty(confirmPassword)) {
            showDialog("", getString(R.string.error_empty_confirm_password));
        } else if (!TextUtils.isEmpty(passwordError)) {
            showDialog("", confirmPasswordError);
        } else if (!confirmPassword.equals(password)) {
            showDialog("", getString(R.string.mismatch_confirm_password));
        } else {
            if (!Utils.isNetworkConnected(requireActivity())) {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
                return;
            }
            progressDialog.show();
            AuthenticationService authenticationService = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
            authenticationService.registration(firstName, lastName, mobileNumber, email, selectedGender, password, getString(R.string.role_id), Utils.CLIENT_ID).enqueue(
                    new Callback<RegistrationResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                            if (response.isSuccessful()) {
                                RegistrationResponse data = response.body();
                                if (data != null) {
                                    if (data.getStatus().equals("Success")) {

                                        String otpMsg = data.getMsg() + " OTP: " + data.getOtp();
                                        resetFields();
                                        if(BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
                                            data.getRsaKeyResponseDetails().setOTPMsg(otpMsg);

                                            data.getRsaKeyResponseDetails().setUserMobileNumber(mobileNumber);
                                            mListener.proceedToPayment(data.getRsaKeyResponseDetails());
                                        } else {
                                            showDialog("", otpMsg, (click, i) -> mListener.validateOTP(mobileNumber, false));
                                        }

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
                            if(t instanceof SocketTimeoutException){
                                showDialog("", getString(R.string.network_slow));
                            } else {
                                showDialog("", t.getMessage());
                            }
                            progressDialog.dismiss();
                        }
                    }
            );
        }
    }

    private void resetFields() {
        tvFirstName.setText("");
        tvLastName.setText("");
        tVMobileNumber.setText("");
        tvEmail.setText("");
        tvPassword.setText("");
        tvConformPassword.setText("");
    }
}