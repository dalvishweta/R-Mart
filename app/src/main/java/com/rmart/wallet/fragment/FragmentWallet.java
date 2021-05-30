package com.rmart.wallet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.databinding.ActivityWalletBinding;
import com.rmart.wallet.viewmodel.WalletViewModel;


public class FragmentWallet extends BaseFragment {

    private WalletViewModel walletViewModel;

    public FragmentWallet() {
        // Required empty public constructor
    }

    public static FragmentWallet newInstance(String param1, String param2) {
        FragmentWallet fragment = new FragmentWallet();
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
        ActivityWalletBinding binding = DataBindingUtil.inflate(inflater, R.layout.activity_wallet, container, false);
        walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        binding.setWalletViewModel(walletViewModel);
        binding.setLifecycleOwner(this);
        walletViewModel.walletTransaction();
        return binding.getRoot();

    }




}