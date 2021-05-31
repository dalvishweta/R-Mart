package com.rmart.authentication.registration.viewmodel;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;

import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.authentication.login.repository.LoginRepository;
import com.rmart.authentication.registration.model.RegisterResponse;
import com.rmart.authentication.registration.repository.RegisterRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class RegisterServicemodule extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> first_name = new MutableLiveData<>();
    public MutableLiveData<String> last_name = new MutableLiveData<>();
    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();
    public MutableLiveData<String> emailid = new MutableLiveData<>();
    public MutableLiveData<String> errorFirst_name = new MutableLiveData<>();
    public MutableLiveData<String> errorLast_name = new MutableLiveData<>();
    public MutableLiveData<String> errorGender = new MutableLiveData<>();
    public MutableLiveData<String> errorEmailid = new MutableLiveData<>();
    public MutableLiveData<RegisterResponse> RegistrationPOJOMutableLiveData = new MutableLiveData<>();

    public void OnClick(final View view) {
        if (validate() && !isLoading.getValue()) {
            isLoading.setValue(true);
            RegisterRepository.getRegistration(first_name.getValue(), last_name.getValue(), gender.getValue(), emailid.getValue(), mobileNumber.getValue(), BuildConfig.ROLE_ID, BuildConfig.CLIENT_ID).observeForever(new Observer<RegisterResponse>() {
                @Override
                public void onChanged(RegisterResponse registerResponse) {
                    if (registerResponse.getCode() == 500) {
                        if (registerResponse.getMsg().equals("Please enter valid email.")) {
                            errorEmailid.setValue(registerResponse.getMsg());
                        } else if (registerResponse.getMsg().equals("Email already exist")) {
                            errorEmailid.setValue(registerResponse.getMsg());
                        }
                    }
                    if (registerResponse.getCode() == 200) {
                        RegistrationPOJOMutableLiveData.postValue(registerResponse);
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
        if (first_name.getValue() == null || first_name.getValue().isEmpty()) {
            errorFirst_name.setValue("Please Enter First Name");
            result = false;
        }
        if (last_name.getValue() == null || last_name.getValue().isEmpty()) {
            errorLast_name.setValue("Please Enter Last Name");
            result = false;
        }
        if (emailid.getValue() == null || emailid.getValue().isEmpty()) {
            errorEmailid.setValue("Please Enter Email ID");
            result = false;
        }

        return result;

    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
        gender.setValue(String.valueOf(parent.getSelectedItem()));

    }

    public void onRegistrationTextChanged(CharSequence s, int start, int before, int count) {
        errorFirst_name.setValue(null);
        errorLast_name.setValue(null);
        errorEmailid.setValue(null);
    }


}

