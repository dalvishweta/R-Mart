package com.rmart.customerservice.mobile.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rmart.R;
import com.rmart.customer.dashboard.viewmodel.HomeViewModel;
import com.rmart.customerservice.mobile.activities.MobileRechargeActivity;
import com.rmart.customerservice.mobile.activities.SelectMobileNumberActivity;
import com.rmart.customerservice.mobile.adapters.RechargeHistoryAdapter;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.viewmodels.MobileRechargeViewModel;
import com.rmart.databinding.FragmentDashBoardBinding;
import com.rmart.databinding.FragmentMobileRechargeBinding;


public class FragmentMobileRecharge extends Fragment {

    FragmentMobileRechargeBinding binding;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mobile_recharge, container, false);
        MobileRechargeViewModel mViewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setMobileRechargeViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.getHistory();
        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
        mViewModel.responseGetHistoryMutableLiveData.observeForever(responseGetHistory -> {
            RechargeHistoryAdapter rechargeHistoryAdapter = new RechargeHistoryAdapter(responseGetHistory.lastTransaction, view -> {
                changefragment(view.getRechargeOn(), view.getRechargeOn(), null);
            });
            binding.setRechargeHistoryAdapter(rechargeHistoryAdapter);
        });

        binding.mobileNo.setOnClickListener((view) -> {
            Intent intent = new Intent(getContext(), SelectMobileNumberActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,200);
        });
        return binding.getRoot();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==200 && data!=null ) {
            String name = data.getStringExtra("name");
            String number = data.getStringExtra("number");
            String type = binding.rgServiceType.getCheckedRadioButtonId()==R.id.rb_prepaid?"Prepaid":"Postpaid";
            name = name==null?number:name;
            changefragment(name, number, type);
        }

    }

    private void changefragment(String name, String number, String type) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, SelectPlanFragment.newInstance(number,name,type)).addToBackStack(null)
                .commit();
    }
}