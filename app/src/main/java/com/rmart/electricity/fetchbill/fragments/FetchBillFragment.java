package com.rmart.electricity.fetchbill.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.databinding.FragmentFetchBillBinding;
import com.rmart.databinding.FragmentSelectOperatorBinding;
import com.rmart.electricity.fetchbill.module.FetchElectricityBillViewModule;
import com.rmart.electricity.selectoperator.model.Operator;
import com.rmart.electricity.selectoperator.module.SelectOperatoreModule;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FetchBillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FetchBillFragment extends Fragment {

    private static final String ARG_OPERATOR = "param2";

    private Operator operator;

    public FetchBillFragment() {
        // Required empty public constructor
    }

    public static FetchBillFragment newInstance( Operator param2) {
        FetchBillFragment fragment = new FetchBillFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OPERATOR, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            operator = (Operator) getArguments().getSerializable(ARG_OPERATOR);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentFetchBillBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fetch_bill, container, false);
        FetchElectricityBillViewModule mViewModel = new ViewModelProvider(this).get(FetchElectricityBillViewModule.class);
        mViewModel.isLoading.setValue(false);
        mViewModel.operatorMutableLiveData.postValue(operator);
        binding.setFetchElectricityBillViewModule(mViewModel);
        binding.setLifecycleOwner(this);
        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
        return binding.getRoot();
    }
}