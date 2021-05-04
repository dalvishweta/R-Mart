package com.rmart.customerservice.mobile.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.customerservice.mobile.viewmodels.PaymentStatusViewModel;
import com.rmart.databinding.FragmentSelectPlan2Binding;
import com.rmart.databinding.PaymentStatusFragmentBinding;
import com.rmart.electricity.CCAvenueResponceModel;

import static com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge.PREPAID;

public class PaymentStatusFragment extends Fragment {
    public static final int SUCCESS=292939;
    public static final int ERROR=292939;
    public static final String ARG_TYPE="ARG_TYPE";
    public static final String ARG_CCAVAINUE="ARG_CCAVAINUE";
    public static final String ARG_NAME="ARG_NAME";
    public static final String ARG_MOBILE="ARG_MOBILE";
    private int messageType;
    private  CCAvenueResponceModel result;
    private  String name;
    private  String mobile;
    private PaymentStatusViewModel mViewModel;
    PaymentStatusFragmentBinding paymentStatusFragmentBinding;

    public static PaymentStatusFragment newInstance(int statusType, CCAvenueResponceModel result,String mobile,String name)
    {
        PaymentStatusFragment paymentStatusFragment = new PaymentStatusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, statusType);
        args.putSerializable(ARG_CCAVAINUE, result);
        args.putString(ARG_MOBILE, mobile);
        args.putString(ARG_NAME, name);
        paymentStatusFragment.setArguments(args);
        return paymentStatusFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            messageType = getArguments().getInt(ARG_TYPE);
            name = getArguments().getString(ARG_NAME);
            mobile = getArguments().getString(ARG_MOBILE);
            result = getArguments().getParcelable(ARG_CCAVAINUE);


        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        paymentStatusFragmentBinding  = DataBindingUtil.inflate(inflater, R.layout.payment_status_fragment, container, false);
        return  paymentStatusFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentStatusViewModel.class);
        mViewModel.ccAvenueResponceModelMutableLiveData.setValue(result);
        mViewModel.messageType.setValue(messageType);
        mViewModel.mobileNumberMutableLiveData.setValue(mobile);
        mViewModel.nameStringMutableLiveData.setValue(name);
        paymentStatusFragmentBinding.setPaymentStatusViewModel(mViewModel);
    }

}