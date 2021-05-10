package com.rmart.customerservice.mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.customerservice.mobile.models.mobileRecharge.RechargeBaseClass;
import com.rmart.customerservice.mobile.viewmodels.PaymentStatusViewModel;
import com.rmart.databinding.PaymentStatusFragmentBinding;
import com.rmart.utilits.IOnBackPressed;

public class PaymentStatusFragment extends Fragment implements IOnBackPressed {
    public static final int SUCCESS=200;
    public static final String ARG_CCAVAINUE="ARG_CCAVAINUE";
    public static final String ARG_NAME="ARG_NAME";
    public static final String ARG_MOBILE="ARG_MOBILE";
    public static final String ARG_AMOUNT="ARG_MOBILE_AMOUNT";
    private  RechargeBaseClass result;
    private  String name;
    private  String mobile;
    private  String amount;
    private PaymentStatusViewModel mViewModel;
    PaymentStatusFragmentBinding paymentStatusFragmentBinding;

    public static PaymentStatusFragment newInstance(RechargeBaseClass result, String mobile, String name, String amount)
    {
        PaymentStatusFragment paymentStatusFragment = new PaymentStatusFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CCAVAINUE, result);
        args.putString(ARG_MOBILE, mobile);
        args.putString(ARG_NAME, name);
        args.putString(ARG_AMOUNT, amount);
        paymentStatusFragment.setArguments(args);
        return paymentStatusFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            mobile = getArguments().getString(ARG_MOBILE);
            amount = getArguments().getString(ARG_AMOUNT);
            result = (RechargeBaseClass) getArguments().getSerializable(ARG_CCAVAINUE);


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
        mViewModel.rechargeBaseClassMutableLiveData.setValue(result);
        mViewModel.mobileNumberMutableLiveData.setValue(mobile);
        mViewModel.nameStringMutableLiveData.setValue(name);
        mViewModel.amount.setValue(amount);

        paymentStatusFragmentBinding.setPaymentStatusViewModel(mViewModel);

        if(result.getStatus()==200){
            paymentStatusFragmentBinding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.mrecharge_success));
        } else {
            paymentStatusFragmentBinding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.payment_failed1));

        }
       /* if(result.getData().getTrackingId()!=null) {
            paymentStatusFragmentBinding.txtTransctionId.setText(result.getData().getTrackingId());
        }*/

    }


    @Override
    public boolean onBackPressed() {

        getActivity().finish();
        return false;
    }
}