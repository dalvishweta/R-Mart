package com.rmart.electricity.billdetails.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.databinding.FragmentBillDetailsBinding;
import com.rmart.databinding.FragmentFetchBillBinding;
import com.rmart.electricity.billdetails.modules.BillDetailsModule;
import com.rmart.electricity.fetchbill.model.BillDetails;
import com.rmart.electricity.fetchbill.module.FetchElectricityBillViewModule;
import com.rmart.electricity.selectoperator.model.Operator;

public class BillDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOBILENO = "param2";
    private static final String UNITS = "param3";
    private static final String BILLDETAILS = "param4";
    private static final String OPERATORS = "param1";

    String  mobileno, units;
    BillDetails billDetails;
    Operator operator;
    public BillDetailsFragment() {
        // Required empty public constructor
    }


    public static BillDetailsFragment newInstance(String mobileno, String units, BillDetails billDetails, Operator operator) {
        BillDetailsFragment fragment = new BillDetailsFragment();
        Bundle args = new Bundle();
        args.putString(MOBILENO, mobileno);
        args.putString(UNITS, units);
        args.putSerializable(BILLDETAILS, billDetails);
        args.putSerializable(OPERATORS, operator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mobileno = getArguments().getString(MOBILENO);
            units = getArguments().getString(UNITS);
            billDetails = (BillDetails) getArguments().getSerializable(BILLDETAILS);
            operator = (Operator) getArguments().getSerializable(OPERATORS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FragmentBillDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_details, container, false);
        BillDetailsModule mViewModel = new ViewModelProvider(this).get(BillDetailsModule.class);
        mViewModel.isLoading.setValue(false);
        mViewModel.operatorMutableLiveData.postValue(operator);
        mViewModel.billDetailsMutableLiveData.postValue(billDetails);
        mViewModel.bill_unit.postValue(units);
        mViewModel.mobilenumber.postValue(mobileno);
        binding.setBillDetailsModule(mViewModel);
        binding.setLifecycleOwner(this);
        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
        return binding.getRoot();
    }
}