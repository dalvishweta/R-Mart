package com.rmart.customerservice.dth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.customerservice.dth.module.DthServicemodule;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.databinding.FragmentMakeDthPaymentBinding;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakeDTHPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeDTHPayment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private Serializable mParam2;
    public MakeDTHPayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment MakeDTHPayment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeDTHPayment newInstance(String vcNumber, Operator operator) {
        MakeDTHPayment fragment = new MakeDTHPayment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, vcNumber);
        args.putSerializable(ARG_PARAM2, operator);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMakeDthPaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_make_dth_payment, container, false);
        DthServicemodule mViewModel = new ViewModelProvider(this).get(DthServicemodule.class);
        mViewModel.isLoading.setValue(false);
        mViewModel.operatorMutableLiveData.postValue((Operator) mParam2);
        binding.setDthServiceViewModule(mViewModel);
        binding.setLifecycleOwner(this);
        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
        return binding.getRoot();
    }
}