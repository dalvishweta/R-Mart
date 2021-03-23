package com.rmart.customerservice.mobile.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.customer.dashboard.viewmodel.HomeViewModel;
import com.rmart.customerservice.mobile.viewmodels.MobileRechargeViewModel;
import com.rmart.databinding.FragmentDashBoardBinding;
import com.rmart.databinding.FragmentMobileRechargeBinding;


public class FragmentMobileRecharge extends Fragment {



    public FragmentMobileRecharge() {
        // Required empty public constructor
    }

    public static FragmentMobileRecharge newInstance(String param1, String param2) {
        FragmentMobileRecharge fragment = new FragmentMobileRecharge();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMobileRechargeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mobile_recharge, container, false);
        MobileRechargeViewModel mViewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setMobileRechargeViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.loadShopHomePage();
        return binding.getRoot();

    }
}