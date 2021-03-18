package com.rmart.electricity.selectoperator.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.databinding.FragmentSelectOperatorBinding;
import com.rmart.electricity.fetchbill.fragments.FetchBillFragment;
import com.rmart.electricity.selectoperator.adapters.OperatorsAdapter;
import com.rmart.electricity.selectoperator.listner.OperatorListner;
import com.rmart.electricity.selectoperator.model.Operator;
import com.rmart.electricity.selectoperator.module.SelectOperatoreModule;

import java.util.ArrayList;

public class SelectOperatorFragment extends Fragment {

    public SelectOperatorFragment() {
    }


    public static SelectOperatorFragment newInstance(String param1, String param2) {
        SelectOperatorFragment fragment = new SelectOperatorFragment();
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
        FragmentSelectOperatorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_operator, container, false);
        SelectOperatoreModule mViewModel = new ViewModelProvider(this).get(SelectOperatoreModule.class);
        binding.setSelectOperatoreModule(mViewModel);
        binding.setLifecycleOwner(this);
        ArrayList< Operator > operators = new ArrayList<>();
        operators.add(new Operator("MSEDC Limited","MSE",R.mipmap.mahavitran));
        operators.add(new Operator("Adani Electricity Mumbai Limited","ADE",R.mipmap.adani));
        OperatorsAdapter homeAdapter = new OperatorsAdapter(getActivity(), operators, new OperatorListner() {
            @Override
            public void OnSelect(Operator operator) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, FetchBillFragment.newInstance(operator)).addToBackStack(null)
                        .commit();
            }
        });
        binding.setMyAdapter(homeAdapter);
        return binding.getRoot();
    }
}