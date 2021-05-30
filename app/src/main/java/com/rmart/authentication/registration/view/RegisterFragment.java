package com.rmart.authentication.registration.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.authentication.registration.model.RegisterResponse;
import com.rmart.authentication.registration.viewmodel.RegisterServicemodule;
import com.rmart.authentication.views.ChangePassword;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.views.CustomerHomeActivity;
import com.rmart.databinding.FragmentRegisterBinding;

import static com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge.PREPAID;


public class RegisterFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /* return inflater.inflate(R.layout.fragment_register, container, false);*/
        FragmentRegisterBinding fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        RegisterServicemodule mViewModel = new ViewModelProvider(this).get(RegisterServicemodule.class);
        mViewModel.isLoading.setValue(false);
        fragmentRegisterBinding.setRegisterServicemodule(mViewModel);
        fragmentRegisterBinding.setLifecycleOwner(this);
        mViewModel.RegistrationPOJOMutableLiveData.observeForever(new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                if (registerResponse.getStatus().equals("Success") && registerResponse.getCode() == 200) {
                    goToCustomerHomeActivity(mViewModel.first_name.getValue(), mViewModel.last_name.getValue(), mViewModel.gender.getValue(), mViewModel.emailid.getValue());
                }
            }
        });
        return fragmentRegisterBinding.getRoot();

    }

    private void goToCustomerHomeActivity(String first_name, String last_name, String gender, String emailid) {
        Intent in = new Intent(getContext(), CustomerHomeActivity.class);
        in.putExtra("full_name", first_name);
        in.putExtra("last_name", last_name);
        in.putExtra("gender", gender);
        in.putExtra("emailid", emailid);
        //in.putExtra("mobile_number",m)
        startActivity(in);
        getActivity().finish();
    }
}