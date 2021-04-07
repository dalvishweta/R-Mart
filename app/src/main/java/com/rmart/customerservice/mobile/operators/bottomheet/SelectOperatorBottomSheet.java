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
import com.rmart.customerservice.mobile.operators.model.OperatorResponse;
import com.rmart.customerservice.mobile.operators.repositories.OpratorsRepository;
import com.rmart.databinding.OpratoreBottomSheetLayoutBinding;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

public class SelectOperatorBottomSheet extends BottomSheetDialogFragment {

    SlectOperator slectOperator ;
    String type ;
    public SelectOperatorBottomSheet(SlectOperator slectOperator,String type){
        this.type=type;
        this.slectOperator=slectOperator;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        OpratoreBottomSheetLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.opratore_bottom_sheet_layout, container, false);
        OpratorsRepository.getOperators(type).observeForever(new Observer<OperatorResponse>() {
            @Override
            public void onChanged(OperatorResponse operatorResponse) {
                ArrayList<Operator> preOperators = new ArrayList<>();
                if(type.equalsIgnoreCase("M_PRE")){
                    preOperators=operatorResponse.operatorData.preOperators;
                } else if(type.equalsIgnoreCase("M_POST")){
                    preOperators= operatorResponse.operatorData.postOperators;

                } else if(type.equalsIgnoreCase("DTH")){
                    preOperators= operatorResponse.operatorData.dthOperators;

                }

                OperatorAdapter operatorAdapter = new OperatorAdapter(getContext(), preOperators,slectOperator);
                binding.setOperatorAdapter(operatorAdapter);
            }
        });
//        ArrayList arrayList = new ArrayList<Operator>();
//
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//        arrayList.add(new Operator("AIRTEL MOBILE BILL","https://testingrokad.msrtcors.com/assets/venus_mart/airtel-tv-icon.png","ATM"));
//
        binding.close.setOnClickListener(view -> SelectOperatorBottomSheet.this.dismiss());
        return binding.getRoot();
    }
}
