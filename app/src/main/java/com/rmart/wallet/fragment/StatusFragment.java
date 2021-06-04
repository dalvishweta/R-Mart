package com.rmart.wallet.fragment;

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
import com.rmart.customerservice.mobile.viewmodels.PaymentStatusViewModel;
import com.rmart.databinding.StatusFragmentBinding;
import com.rmart.utilits.IOnBackPressed;
import com.rmart.wallet.model.CheckWalletTopup;

import java.io.Serializable;

public class StatusFragment extends Fragment implements IOnBackPressed {
    public static final int SUCCESS=200;
    public static final String ARG_CCAVAINUE="ARG_CCAVAINUE";
    public static final String ARG_NAME="ARG_NAME";
    public static final String ARG_MOBILE="ARG_MOBILE";
    public static final String ARG_AMOUNT="ARG_MOBILE_AMOUNT";
    private  CheckWalletTopup result;
    private  String name;
    private  String mobile;
    private  String amount;
    private PaymentStatusViewModel mViewModel;
    private StatusFragmentBinding statusFragmentBinding;

    public static StatusFragment newInstance(CheckWalletTopup result)
    {
        StatusFragment paymentStatusFragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CCAVAINUE, (Serializable) result);

        paymentStatusFragment.setArguments(args);
        return paymentStatusFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            result = (CheckWalletTopup) getArguments().getSerializable(ARG_CCAVAINUE);


        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        statusFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.status_fragment, container, false);
        return  statusFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentStatusViewModel.class);
        mViewModel.WalletTOpUpClassMutableLiveData.setValue(result);
        statusFragmentBinding.setPaymentStatusViewModel(mViewModel);
        statusFragmentBinding.setLifecycleOwner(this);
        if(result.getCode()==200){
            statusFragmentBinding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.mrecharge_success));
        } else {
            statusFragmentBinding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.payment_failed1));

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