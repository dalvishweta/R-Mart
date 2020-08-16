package com.rmart.orders.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.viewmodel.MyOrdersViewModel;

import java.util.Objects;

public class ProcessToDeliveryFragment extends BaseOrderFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OrderObject orderObject;
    private String mParam2;
    private AppCompatEditText etvReceivedAmount;
    MyOrdersViewModel myOrdersViewModel;
    public ProcessToDeliveryFragment() {
        // Required empty public constructor
    }

    public static ProcessToDeliveryFragment newInstance(OrderObject param1, String param2) {
        ProcessToDeliveryFragment fragment = new ProcessToDeliveryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderObject = (OrderObject) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myOrdersViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyOrdersViewModel.class);
        return inflater.inflate(R.layout.fragment_process_to_delivery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etvReceivedAmount = view.findViewById(R.id.received_amount);
        if(!orderObject.isDue()) {
            etvReceivedAmount.setVisibility(View.GONE);
        }
        view.findViewById(R.id.submit).setOnClickListener(view1 -> {
            if(!orderObject.isDue()) {
                mListener.goToOTPValidation(orderObject);
            } else {
                String receivedAmount = Objects.requireNonNull(etvReceivedAmount.getText()).toString();
                if (receivedAmount.equals("")) {
                    showDialog("Sorry...", getString(R.string.error_enpty_received_amount));
                } else if(!receivedAmount.equals(orderObject.getTotalAmount())) {
                    showDialog("Sorry...", String.format(getResources().getString(R.string.received_amount_error), receivedAmount, orderObject.getTotalAmount()));
                } else {
                    mListener.goToOTPValidation(orderObject);
                }
            }

        });
    }
}