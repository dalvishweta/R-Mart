package com.rmart.customerservice.mobile.operators.bottomheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rmart.R;
import com.rmart.customerservice.mobile.listners.SlectOperator;
import com.rmart.customerservice.mobile.operators.adapter.OperatorAdapter;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.databinding.OpratoreBottomSheetLayoutBinding;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class SelectOperatorBottomSheet extends BottomSheetDialogFragment {

    SlectOperator slectOperator ;
    public SelectOperatorBottomSheet(SlectOperator slectOperator){
        this.slectOperator=slectOperator;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        OpratoreBottomSheetLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.opratore_bottom_sheet_layout, container, false);
        ArrayList arrayList = new ArrayList<Operator>();
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        OperatorAdapter operatorAdapter = new OperatorAdapter(getContext(), arrayList,slectOperator);
        binding.setOperatorAdapter(operatorAdapter);
        binding.close.setOnClickListener(view -> SelectOperatorBottomSheet.this.dismiss());
        return binding.getRoot();
    }
}
