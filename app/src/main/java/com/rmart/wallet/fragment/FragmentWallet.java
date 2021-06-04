package com.rmart.wallet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.views.ServicePaymentActivity;
import com.rmart.databinding.ActivityWalletBinding;
import com.rmart.profile.model.MyProfile;
import com.rmart.wallet.model.CheckWalletTopup;
import com.rmart.wallet.viewmodel.WalletViewModel;
import com.rmart.wallet.viewmodel.repository.WalletRepository;

import static com.rmart.customerservice.mobile.views.ServicePaymentActivity.RESULT;


public class FragmentWallet extends BaseFragment {

    private WalletViewModel walletViewModel;

    public FragmentWallet() {
        // Required empty public constructor
    }

    public static FragmentWallet newInstance(String param1, String param2) {
        FragmentWallet fragment = new FragmentWallet();
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
        ActivityWalletBinding binding = DataBindingUtil.inflate(inflater, R.layout.activity_wallet, container, false);
        walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        binding.setWalletViewModel(walletViewModel);
        binding.setLifecycleOwner(this);
        binding.toolbar.setOnClickListener(view -> getActivity().onBackPressed());

        walletViewModel.isLoading.setValue(false);
        walletViewModel.walletTransaction(getContext());
        walletViewModel.responseRsakeyMutableLiveData.observeForever(rsaKeyResponse -> {

            if (rsaKeyResponse != null && rsaKeyResponse.getStatus().equals("Success")) {
                Intent ii = new Intent(getContext(), ServicePaymentActivity.class);
                ii.putExtra("rsakeyresonse", rsaKeyResponse.getData());
                ii.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(ii, 3333);
            } else {

                Toast.makeText(getContext(), "Some Thing Wrong Please Try After Some Time", Toast.LENGTH_LONG).show();
            }
            walletViewModel.isLoading.setValue(false);
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
                //Toast.makeText(getContext(), "" + result, Toast.LENGTH_LONG).show();
            } else {
                recharge(result);
                //Toast.makeText(getContext(), "" + result, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Payment Gateway transaction Cancel", Toast.LENGTH_LONG).show();
        }

    }

    private void recharge(String data) {
        ProgressDialog progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        WalletRepository.doWalletRecharge(MyProfile.getInstance(getContext()).getUserID(), MyProfile.getInstance(getContext()).getWallet_id(), data).observeForever(new Observer<CheckWalletTopup>() {
            @Override
            public void onChanged(CheckWalletTopup rechargeBaseClass) {
                // API is not following Restfull gaidlines  so it may cause Error or Exception In future witch may cause in app crashhh
                displayStatus(rechargeBaseClass);
                walletViewModel.isLoading.setValue(false);
                progressdialog.dismiss();
            }
        });
    }

    private void displayStatus(CheckWalletTopup paymentResponse) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, StatusFragment.newInstance(paymentResponse))
                .commit();
    }


}