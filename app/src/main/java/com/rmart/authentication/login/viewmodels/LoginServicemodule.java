package com.rmart.authentication.login.viewmodels;

import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.authentication.login.repository.LoginRepository;

public class LoginServicemodule extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<com.rmart.utilits.pojos.LoginResponse> VerifyOTPPOJOMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> mobile_numberr = new MutableLiveData<>();
    public MutableLiveData<String> errorMobileNumber = new MutableLiveData<>();
    public MutableLiveData<LoginResponse> LoginPOJOMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isVisibleLogin = new MutableLiveData<>();
    public MutableLiveData<Boolean> isVisibleOTP = new MutableLiveData<>();
    public MutableLiveData<String> device_id = new MutableLiveData<>();
    public MutableLiveData<String> oneOtp = new MutableLiveData<>();
    public MutableLiveData<String> twoOtp = new MutableLiveData<>();
    public MutableLiveData<String> threeOtp = new MutableLiveData<>();
    public MutableLiveData<String> fourOtp = new MutableLiveData<>();

    public void onClick(final View view) {

        switch(view.getId()) {
            case R.id.login:
                if (validate() && !isLoading.getValue()) {
                    isLoading.setValue(true);

                    LoginRepository.getLoginInfo(mobile_numberr.getValue(), BuildConfig.ROLE_ID).observeForever(new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            if (loginResponse.getCode() == 200) {
                                isLoading.setValue(false);
                                isVisibleOTP.setValue(true);
                                isVisibleLogin.setValue(false);
                                LoginPOJOMutableLiveData.postValue(loginResponse);
                            }
                            else {
                                isLoading.setValue(false);
                                isVisibleOTP.setValue(false);
                                isVisibleLogin.setValue(true);
                                LoginPOJOMutableLiveData.postValue(loginResponse);
                            }
                        }
                    });
                } else {
                    isLoading.setValue(false);
                }
                break;
            case R.id.verify_otp:
                if (validate() && !isLoading.getValue()) {
                    isLoading.setValue(true);
                    Log.d("mobile_number",mobile_numberr.getValue());
                    System.out.println(device_id.getValue());
                    String otp = oneOtp.getValue()+twoOtp.getValue()+threeOtp.getValue()+fourOtp.getValue();
                    LoginRepository.VerifyOTP(mobile_numberr.getValue(),BuildConfig.ROLE_ID,otp,device_id.getValue()).observeForever(new Observer<com.rmart.utilits.pojos.LoginResponse>() {
                        @Override
                        public void onChanged(com.rmart.utilits.pojos.LoginResponse loginResponse) {
                            if (loginResponse.getStatus().equalsIgnoreCase("success")) {
                                VerifyOTPPOJOMutableLiveData.postValue(loginResponse);
                            }
                            if (loginResponse.getMsg().equalsIgnoreCase("Wrong OTP")){
                                oneOtp.setValue(null);
                                twoOtp.setValue(null);
                                threeOtp.setValue(null);
                                fourOtp.setValue(null);
                            }
                            isLoading.setValue(false);
                            VerifyOTPPOJOMutableLiveData.postValue(loginResponse);
                        }
                    });
                } else {
                    isLoading.setValue(false);
                }
                break;
            case R.id.forgot_password:
            {
                if (validate() && !isLoading.getValue()) {
                    isLoading.setValue(true);

                    LoginRepository.getLoginInfo(mobile_numberr.getValue(), BuildConfig.ROLE_ID).observeForever(new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            if (loginResponse.getCode() == 200) {
                                isLoading.setValue(false);
                                isVisibleOTP.setValue(true);
                                isVisibleLogin.setValue(false);
                                LoginPOJOMutableLiveData.postValue(loginResponse);
                            }
                            else {
                                isLoading.setValue(false);
                                isVisibleOTP.setValue(false);
                                isVisibleLogin.setValue(true);
                                LoginPOJOMutableLiveData.postValue(loginResponse);
                            }

                        }
                    });
                } else {
                    isLoading.setValue(false);
                }
            }
            break;


        }
    }

    public boolean validate() {
        boolean result = true;
        if (mobile_numberr.getValue() == null || mobile_numberr.getValue().isEmpty() || mobile_numberr.getValue().length() < 10) {
            errorMobileNumber.setValue("Please enter valid Mobile Number.");
            result = false;
        }

        return result;
    }
    public void onCumsumerNumberTextChanged(CharSequence s, int start, int before, int count) {
        errorMobileNumber.setValue(null);
    }

    View.OnKeyListener key=new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(!((EditText) v).toString().isEmpty())
                v.focusSearch(View.FOCUS_RIGHT).requestFocus();

            return false;
        }
    };

}
