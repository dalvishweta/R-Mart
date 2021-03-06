package com.rmart.customerservice.dth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.dth.viewmodels.DthServicemodule;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.databinding.ActivityDthSubscriptitonNumberBinding;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDTHServiceNumber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    FragmentDTHServiceNumber extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;
    private String mParam2;

    public FragmentDTHServiceNumber() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDTHServiceNumber.
     */
    // TODO: Rename and change types and number of pa322ewrameters
    public static FragmentDTHServiceNumber newInstance(String param1, String param2) {
        FragmentDTHServiceNumber fragment = new FragmentDTHServiceNumber();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static BaseFragment newInstance(Operator operator) {
        FragmentDTHServiceNumber fragment = new FragmentDTHServiceNumber();
        Bundle args = new Bundle();
        args.putSerializable("operator", (Serializable) operator);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable("operator");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActivityDthSubscriptitonNumberBinding binding = DataBindingUtil.inflate(inflater, R.layout.activity_dth_subscriptiton_number, container, false);
        DthServicemodule mViewModel = new ViewModelProvider(this).get(DthServicemodule.class);
        mViewModel.isLoading.setValue(false);
        mViewModel.operatorMutableLiveData.postValue((Operator) mParam1);
        binding.setDthServiceViewModule(mViewModel);
        binding.setLifecycleOwner(this);
        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
        return binding.getRoot();
    }
}