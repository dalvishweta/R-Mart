package com.rmart.customerservice.mobile.circle.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rmart.R;
import com.rmart.customerservice.mobile.circle.adapter.CircleAdapter;
import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.listners.SlectCircle;
import com.rmart.customerservice.mobile.listners.SlectOperator;
import com.rmart.customerservice.mobile.operators.adapter.OperatorAdapter;
import com.rmart.customerservice.mobile.operators.bottomheet.SelectOperatorBottomSheet;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.databinding.CircleBottomSheetLayoutBinding;
import com.rmart.databinding.OpratoreBottomSheetLayoutBinding;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class SelectCircleBottomSheet extends BottomSheetDialogFragment {
    SlectCircle slectCircle ;
    public SelectCircleBottomSheet(SlectCircle slectCircle){
        this.slectCircle=slectCircle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        CircleBottomSheetLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.circle_bottom_sheet_layout, container, false);
        ArrayList arrayList = new ArrayList<Circle>();
        arrayList.add(new Circle("Andhra Pradesh Telangana","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Assam","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        arrayList.add(new Circle("Bihar Jharkhand","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
        CircleAdapter operatorAdapter = new CircleAdapter(getContext(), arrayList,slectCircle);
        binding.setCircleAdapter(operatorAdapter);
        binding.close.setOnClickListener(view -> SelectCircleBottomSheet.this.dismiss());

        return binding.getRoot();
    }
}
