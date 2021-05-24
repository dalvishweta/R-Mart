package com.rmart.wallet.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;

public class BillingHistoryFragment extends BaseFragment {
    Toolbar toolbar;
    Context context;
    public BillingHistoryFragment() {
        // Required empty public constructor
    }
    public static BillingHistoryFragment getInstance() {
        BillingHistoryFragment billingHistoryFragment = new BillingHistoryFragment();
        Bundle extras = new Bundle();
        billingHistoryFragment.setArguments(extras);
        return billingHistoryFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_billing_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);


    }
}