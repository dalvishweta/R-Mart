package com.rmart.authentication.registration.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.authentication.login.view.LoginFragment;
import com.rmart.authentication.registration.model.RegisterResponse;
import com.rmart.authentication.registration.viewmodel.RegisterServicemodule;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.databinding.FragmentRegisterBinding;


public class RegisterFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private String mobile;
    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mobile = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /* return inflater.inflate(R.layout.fragment_register, container, false);*/
        FragmentRegisterBinding fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        RegisterServicemodule mViewModel = new ViewModelProvider(this).get(RegisterServicemodule.class);
        mViewModel.mobileNumber.setValue(mobile);
        mViewModel.isLoading.setValue(false);
        fragmentRegisterBinding.setRegisterServicemodule(mViewModel);
        fragmentRegisterBinding.setLifecycleOwner(this);
        mViewModel.RegistrationPOJOMutableLiveData.observeForever(new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                if (registerResponse.getStatus().equals("Success") && registerResponse.getCode() == 200) {
                    goToLogin();
                }
            }
        });
        return fragmentRegisterBinding.getRoot();

    }

    private void goToLogin() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_container, LoginFragment.newInstance("","")).addToBackStack(null)
                .commit();
    }
}