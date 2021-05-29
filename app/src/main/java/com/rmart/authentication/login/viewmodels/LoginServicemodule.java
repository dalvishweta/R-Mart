package com.rmart.authentication.login.viewmodels;

import android.view.View;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.authentication.login.repository.LoginRepository;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class LoginServicemodule extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<LoginResponse> LoginPOJOMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> mobile_number = new MutableLiveData<>();
    public MutableLiveData<String> errorMobileNumber = new MutableLiveData<>();


    public void onClick(final View view) {

        if (validate() && !isLoading.getValue()) {
            isLoading.setValue(true);
            LoginRepository.getLoginInfo(mobile_number.getValue(), BuildConfig.ROLE_ID).observeForever(new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse) {
                    if (loginResponse.getCode() == 200) {
                        LoginPOJOMutableLiveData.postValue(loginResponse);
                    }
                    isLoading.setValue(false);
                }
            });
        } else {
            isLoading.setValue(false);
        }
    }

    public boolean validate() {
        boolean result = true;
        if (mobile_number.getValue() == null || mobile_number.getValue().isEmpty() || mobile_number.getValue().length() > 10) {
            errorMobileNumber.setValue(String.valueOf(R.string.error_mobile));
            result = false;
        }

        return result;
    }

}
