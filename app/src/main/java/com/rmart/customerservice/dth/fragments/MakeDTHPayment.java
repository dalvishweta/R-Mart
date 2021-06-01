package com.rmart.customerservice.dth.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.customerservice.dth.model.DthResponse;
import com.rmart.customerservice.dth.viewmodels.DTHRechargeMakePaymentViewModel;
import com.rmart.customerservice.mobile.fragments.PaymentStatusFragment;
import com.rmart.customerservice.mobile.models.mobileRecharge.RechargeBaseClass;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.customerservice.mobile.repositories.RechargeRepository;
import com.rmart.customerservice.mobile.views.ServicePaymentActivity;
import com.rmart.databinding.FragmentMakeDthPaymentBinding;
import com.rmart.profile.model.MyProfile;

import static com.rmart.customerservice.mobile.views.ServicePaymentActivity.RESULT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakeDTHPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeDTHPayment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String vcNumber;
    private Operator operator;
    private DthResponse dthResponse;
    DTHRechargeMakePaymentViewModel mViewModel;

    public MakeDTHPayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MakeDTHPayment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeDTHPayment newInstance(String vcNumber, Operator operator, DthResponse dthResponse) {
        MakeDTHPayment fragment = new MakeDTHPayment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, vcNumber);
        args.putSerializable(ARG_PARAM2, operator);
        args.putSerializable(ARG_PARAM3, dthResponse);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vcNumber = getArguments().getString(ARG_PARAM1);
            operator = (Operator) getArguments().getSerializable(ARG_PARAM2);
            dthResponse = (DthResponse) getArguments().getSerializable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMakeDthPaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_make_dth_payment, container, false);
        mViewModel = new ViewModelProvider(this).get(DTHRechargeMakePaymentViewModel.class);
        mViewModel.isLoading.setValue(false);
        mViewModel.operatorMutableLiveData.setValue(operator);
        mViewModel.cumsumerNumber.setValue(vcNumber);
        mViewModel.dthPOJOMutableLiveData.setValue(dthResponse);
        binding.setDTHRechargeMakePaymentViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.responseRsakeyMutableLiveData.observeForever(rsaKeyResponse -> {

            if (rsaKeyResponse != null && rsaKeyResponse.getStatus().equals("success")) {
                if (rsaKeyResponse.getData().getCcavenue() == 1) {
                    Intent ii = new Intent(getContext(), ServicePaymentActivity.class);
                    ii.putExtra("rsakeyresonse", rsaKeyResponse.getData());
                    ii.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(ii, 3333);
                } else {

                }
            } else {

                Toast.makeText(getContext(), "Some Thing Wrong Please Try After Some Time", Toast.LENGTH_LONG).show();
            }
            mViewModel.isLoading.setValue(false);
        });
        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String result = data.getStringExtra(RESULT);
            //Note:-- suggesion dont use directly rokad.in server make call from Rokadmart server using proxy method and keep transaction status update with rokad mart
            if (result != null && requestCode == 3333 && resultCode == ServicePaymentActivity.RESULT_OK) {
                ///JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                recharge(result);
            } else {
                recharge(result);
            }
        } else {
            Toast.makeText(getContext(), "Payment Gateway transaction Cancel", Toast.LENGTH_LONG).show();
        }

    }

    private void recharge(String data) {
        ProgressDialog progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        RechargeRepository.doMobileRecharge(RechargeRepository.SERVICE_TYPE_DTH_RECHARGE, vcNumber, 0, operator.type, null, null, RechargeRepository.PLAN_TYPE_SPECIAL_RECHARGE, "10" + "", MyProfile.getInstance(getContext()).getUserID(), data,1,1,false).observeForever(new Observer<RechargeBaseClass>() {
            @Override
            public void onChanged(RechargeBaseClass rechargeBaseClass) {
                // API is not following Restfull gaidlines  so it may cause Error or Exception In future witch may cause in app crashhh
                displayStatus(rechargeBaseClass);
                mViewModel.isLoading.setValue(false);
                progressdialog.dismiss();
            }
        });
    }

    private void displayStatus(RechargeBaseClass paymentResponse) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, PaymentStatusFragment.newInstance(paymentResponse, null, null, String.valueOf(mViewModel.cumsumerAmount.getValue())))
                .commit();
    }
}

