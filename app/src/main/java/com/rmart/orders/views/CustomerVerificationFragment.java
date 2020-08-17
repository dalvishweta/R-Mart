package com.rmart.orders.views;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.authentication.views.OTPFragment;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.viewmodel.MyOrdersViewModel;

import java.util.Objects;

import static com.rmart.authentication.views.OTPFragment.INT_OTP_LENGTH;

public class CustomerVerificationFragment extends BaseOrderFragment implements TextWatcher {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OrderObject orderObject;
    private String mParam2;
    private AppCompatEditText otpEditText;

    public CustomerVerificationFragment() {
        // Required empty public constructor
    }

    public static CustomerVerificationFragment newInstance(OrderObject param1, String param2) {
        CustomerVerificationFragment fragment = new CustomerVerificationFragment();
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
        return inflater.inflate(R.layout.fragment_customer_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        otpEditText = view.findViewById(R.id.received_otp);
        otpEditText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Toast.makeText(getContext(), charSequence, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if( s.toString().length() == 4) {
            updateToDelivered();
        }
    }

    private void updateToDelivered() {
        orderObject.setOrderType(getResources().getString(R.string.delivered));
        MyOrdersViewModel viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyOrdersViewModel.class);
        viewModel.setDeliveredOrders(orderObject);
        mListener.goToHome();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}