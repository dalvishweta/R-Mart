package com.rmart.authentication.registration.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.authentication.registration.viewmodel.RegisterServicemodule;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.databinding.FragmentRegisterBinding;
import com.rmart.profile.model.MyProfile;
import com.rmart.profile.views.MyProfileActivity;
import com.rmart.utilits.pojos.RegisterResponse;
import com.rmart.utilits.pojos.ProfileResponse;


public class RegisterFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private String mobile, refer_no, refer_user_id, device_id;
    SharedPreferences sp;
    ProfileResponse profileResponse;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String mMobileNumber, String refer_no, String refer_user_id, String device_id) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mMobileNumber);
        args.putString(ARG_PARAM2, refer_no);
        args.putString(ARG_PARAM3, refer_user_id);
        args.putString(ARG_PARAM4, device_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mobile = getArguments().getString(ARG_PARAM1);
            refer_no = getArguments().getString(ARG_PARAM2);
            refer_user_id = getArguments().getString(ARG_PARAM3);
            device_id = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /* return inflater.inflate(R.layout.fragment_register, container, false);*/
        FragmentRegisterBinding fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        sp = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        RegisterServicemodule mViewModel = new ViewModelProvider(this).get(RegisterServicemodule.class);
        mViewModel.mobileNumber.setValue(mobile);
        mViewModel.refer_no.setValue(refer_no);
        mViewModel.refer_user_id.setValue(refer_user_id);
        mViewModel.device_id.setValue(device_id);
        mViewModel.isLoading.setValue(false);
        fragmentRegisterBinding.setRegisterServicemodule(mViewModel);
        fragmentRegisterBinding.setLifecycleOwner(this);

        mViewModel.RegistrationPOJOMutableLiveData.observeForever(new Observer<com.rmart.authentication.registration.model.RegisterResponse>() {
            @Override
            public void onChanged(com.rmart.authentication.registration.model.RegisterResponse registerResponse) {
                Log.d("RFERE", registerResponse.getMsg());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("userLoginStatus", "yes");
                editor.commit();
                editor.apply();
                profileResponse = registerResponse.getData();
                MyProfile.setInstance(getActivity(),profileResponse);
                goToLogin();
                Log.d("EMAIl", registerResponse.getMsg());
            }
        });


        return fragmentRegisterBinding.getRoot();

    }

    private void goToLogin() {
        Intent intent = new Intent(getContext(), MyProfileActivity.class);
        intent.putExtra("IsNewAddress", true);
        getActivity().startActivity(intent);
       /* getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_container, LoginFragment.newInstance("","")).addToBackStack(null)
                .commit();*/
    }
}