package com.rmart.authentication.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.customer.adapters.CustomSpinnerAdapter;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.RegistrationFeeStructure;
import com.rmart.utilits.pojos.RegistrationResponse;
import com.rmart.utilits.services.AuthenticationService;
import com.rmart.utilits.services.RegPayAmtResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends LoginBaseFragment implements View.OnClickListener {

    private AppCompatEditText tvFirstName,customamount, tvLastName, tVMobileNumber, tvEmail,  tvConformPassword;
    private String selectedGender,tvPassword;
    EditText editTextOne,editTextTwo,editTextThree,editTextFour,confirm_editTextOne,confirm_editTextTwo,confirm_editTextThree,confirm_editTextFour;
    TextView login,default_amaount;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    private void getFeeDetails(LinearLayout paymentBase) {

//        String json= "{\n" +
//                "    \"status\": \"success\",\n" +
//                "    \"msg\": \"Registration payment details\",\n" +
//                "    \"Code\": \"200\",\n" +
//                "    \"data\": [\n" +
//                "        {\n" +
//                "            \"pay_type\": \"Montly(Inc GST)\",\n" +
//                "            \"amount\": \"700\",\n" +
//                "            \"position\": 1\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"pay_type\": \"Quarterly(Inc GST)\",\n" +
//                "            \"amount\": \"2000\",\n" +
//                "            \"position\": 2\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"pay_type\": \"Half-yearly(Inc GST)\",\n" +
//                "            \"amount\": \"3540\",\n" +
//                "            \"position\": 3\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"pay_type\": \"Annual(Inc GST)\",\n" +
//                "            \"amount\": \"6000\",\n" +
//                "            \"position\": 4\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"request_id\": 55414\n" +
//                "}";
//
//        Gson gson = new Gson();
//        RegPayAmtResponse obj2 = gson.fromJson(json, RegPayAmtResponse.class);
//        registrationFeeStructuresList = obj2.getPaymentObjects();
//
//        for (RegistrationFeeStructure feeStructure : registrationFeeStructuresList) {
//                View v = View.inflate(requireActivity(), R.layout.layout, null);
//                ((AppCompatTextView) v.findViewById(R.id.pay_type)).setText(feeStructure.getPayType());
//                ((AppCompatTextView) v.findViewById(R.id.pay_amt)).setText("Rs. "+String.format("%.2f",Double.parseDouble(feeStructure.getAmount())));
//                paymentBase.addView(v);
//            }
                progressDialog.show();
        AuthenticationService service = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationService.class);
        service.regPayAmt().enqueue(new Callback<RegPayAmtResponse>() {
            @Override
            public void onResponse(Call<RegPayAmtResponse> call, Response<RegPayAmtResponse> response) {
                if (response.isSuccessful()) {
                    RegPayAmtResponse data = response.body();
                    assert data != null;
                    registrationFeeStructuresList.clear();
                    registrationFeeStructuresList = data.getPaymentObjects();
                    paymentBase.removeAllViews();
                    paymentBase.setVisibility(View.VISIBLE);
                    for (RegistrationFeeStructure feeStructure : registrationFeeStructuresList) {
                        View v = View.inflate(requireActivity(), R.layout.layout, null);
                        ((AppCompatTextView) v.findViewById(R.id.pay_type)).setText(feeStructure.getPayType());
                        ((AppCompatTextView) v.findViewById(R.id.pay_amt)).setText("Rs. "+String.format("%.2f",Double.parseDouble(feeStructure.getAmount())));
                        paymentBase.addView(v);
                    }
                } else {
                    showDialog("", response.message());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RegPayAmtResponse> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    showDialog("", getString(R.string.network_slow));
                } else {
                    showDialog("", t.getMessage());
                }
                progressDialog.dismiss();
            }
        });

        /*RegistrationFeeStructure registration = new RegistrationFeeStructure();
        registration.setPayType(getString(R.string.registration_fee));
        registration.setAmount("2000");

        RegistrationFeeStructure gst = new RegistrationFeeStructure();
        gst.setPayType("GST 18%");
        gst.setAmount("360");

        RegistrationFeeStructure total = new RegistrationFeeStructure();
        total.setPayType(getString(R.string.total_amount));
        total.setAmount("2360");*/

        /*registrationFeeStructuresList.add(registration);
        registrationFeeStructuresList.add(gst);
        registrationFeeStructuresList.add(total);*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button register = view.findViewById(R.id.register);
        register.setOnClickListener(this);
        tvFirstName = view.findViewById(R.id.first_name);
        customamount = view.findViewById(R.id.customamount);
        default_amaount = view.findViewById(R.id.default_amaount);
        login = view.findViewById(R.id.login);
        tvLastName = view.findViewById(R.id.lase_name);
        tVMobileNumber = view.findViewById(R.id.mobile_number);
        tvEmail = view.findViewById(R.id.email);
        editTextOne = view.findViewById(R.id.editTextOne);
        editTextTwo = view.findViewById(R.id.editTextTwo);
        editTextThree = view.findViewById(R.id.editTextThree);
        editTextFour = view.findViewById(R.id.editTextFour);
        confirm_editTextOne = view.findViewById(R.id.confirm_password_editTextOne);
        confirm_editTextTwo = view.findViewById(R.id.confirm_password_editTextTwo);
        confirm_editTextThree = view.findViewById(R.id.confirm_password_editTextThree);
        confirm_editTextFour = view.findViewById(R.id.confirm_password_editTextFour);
        //tvPassword = view.findViewById(R.id.password);
        //tvConformPassword = view.findViewById(R.id.confirm_password);
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

        CustomSpinnerAdapter customStringAdapter = new CustomSpinnerAdapter(requireActivity(), gendersList,false);
        genderSpinnerField.setAdapter(customStringAdapter);

        LinearLayout paymentBase = view.findViewById(R.id.payment_base);
        paymentBase.removeAllViews();
        if (BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID)) {
            getFeeDetails(paymentBase);
            register.setText(R.string.proceed_to_pay);
        } else {
            view.findViewById(R.id.tv_payment_information_field).setVisibility(View.GONE);
            paymentBase.setVisibility(View.GONE);
            customamount.setVisibility(View.GONE);
            default_amaount.setVisibility(View.GONE);

            register.setText(R.string.register_right);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.base_container, LoginFragment.newInstance("", ""), "login");

                fragmentTransaction.commit();
            }
        });
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
        String amount = Objects.requireNonNull(customamount.getText()).toString().trim();
        email = Objects.requireNonNull(tvEmail.getText()).toString().trim();
        password = Objects.requireNonNull(editTextOne.getText().toString().trim()+editTextTwo.getText().toString().trim()+editTextThree.getText().toString().trim()+editTextFour.getText().toString().trim()).toString().trim();
        confirmPassword = Objects.requireNonNull(confirm_editTextOne.getText().toString().trim()+confirm_editTextTwo.getText().toString().trim()+confirm_editTextThree.getText().toString().trim()+confirm_editTextFour.getText().toString().trim()).toString().trim();
        String passwordError = Utils.isValidPassword(password);
        String confirmPasswordError = Utils.isValidPassword(confirmPassword);

        Double amount2 = Double.valueOf(0);
        try{
            amount2=  Double.parseDouble(amount);
        } catch (Exception e){

        }

        if (firstName.length() <= 2) {
            showDialog("", getString(R.string.error_full_name));
        } else if (lastName.length() <= 2) {
            showDialog("", getString(R.string.error_last_name));
        }
        else if ( BuildConfig.ROLE_ID.equalsIgnoreCase(Utils.RETAILER_ID) && amount2 < Double.valueOf(100.00)) {
            showDialog("", "Please enter Amount" );
        }
        else if (TextUtils.isEmpty(selectedGender) || selectedGender.equalsIgnoreCase(Utils.SELECT_YOUR_GENDER)) {
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
            authenticationService.registration(firstName, lastName, mobileNumber, email, selectedGender, password, getString(R.string.role_id), Utils.CLIENT_ID,amount).enqueue(
                    new Callback<RegistrationResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                            if (response.isSuccessful()) {
                                RegistrationResponse data = response.body();
                                if (data != null) {
                                    if (data.getStatus().equals("Success")) {

                                        String otpMsg = data.getMsg() + " OTP: " + data.getOtp();

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
        //tvPassword.setText("");
        tvConformPassword.setText("");
    }
}