package com.rmart.authentication.login.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.authentication.login.model.LoginResponse;
import com.rmart.authentication.login.viewmodels.LoginServicemodule;
import com.rmart.authentication.views.LoginBaseFragment;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.databinding.FragmentLoginBinding;


public class LoginFragment extends LoginBaseFragment {



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       /* return inflater.inflate(R.layout.fragment_login, container, false);*/
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        LoginServicemodule mViewModel = new ViewModelProvider(this).get(LoginServicemodule.class);
        mViewModel.isLoading.setValue(false);
        binding.setLoginServiceModule(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.LoginPOJOMutableLiveData.observeForever(new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.getStatus().equals("success") && loginResponse.getOtp()==200){

                }
            }
        });
        return binding.getRoot();

    }
}