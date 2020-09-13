package com.rmart.customer.views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerHomeInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakePaymentFragment#getInstance} factory method to
 * create an instance of this fragment.
 */
public class MakePaymentFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView ivCashOnDeliveryImageField;
    private ImageView ivInternetBankingImageField;
    private ImageView ivMyWalletImageField;
    private int selectedPaymentType = -1;
    private OnCustomerHomeInteractionListener listener;

    public MakePaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakePaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakePaymentFragment getInstance(String param1, String param2) {
        MakePaymentFragment fragment = new MakePaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerHomeInteractionListener) {
            listener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        ivCashOnDeliveryImageField = view.findViewById(R.id.iv_cash_on_delivery_field);
        ivInternetBankingImageField = view.findViewById(R.id.iv_internet_banking_field);
        ivMyWalletImageField= view.findViewById(R.id.iv_my_wallet_field);

        view.findViewById(R.id.cash_on_delivery_layout_field).setOnClickListener(v -> {
            cashOnDeliverySelected();
        });
        view.findViewById(R.id.internet_banking_layout_field).setOnClickListener(v -> {
            internetBankingSelected();
        });
        view.findViewById(R.id.my_wallet_layout_field).setOnClickListener(v -> {
            myWalletSelected();
        });
        view.findViewById(R.id.btn_proceed_field).setOnClickListener(v -> {
            proceedSelected();
        });
    }

    private void cashOnDeliverySelected() {
        resetItems();
        selectedPaymentType = 0;
        ivCashOnDeliveryImageField.setImageResource(R.drawable.ic_checked);
    }

    private void internetBankingSelected() {
        resetItems();
        selectedPaymentType = 1;
        ivCashOnDeliveryImageField.setImageResource(R.drawable.ic_checked);
    }

    private void myWalletSelected() {
        resetItems();
        selectedPaymentType = 2;
        ivInternetBankingImageField.setImageResource(R.drawable.ic_checked);
    }

    private void resetItems() {
        ivCashOnDeliveryImageField.setImageResource(R.drawable.ic_un_checked);
        ivInternetBankingImageField.setImageResource(R.drawable.ic_un_checked);
        ivMyWalletImageField.setImageResource(R.drawable.ic_un_checked);
    }

    private void proceedSelected() {
        if(selectedPaymentType == -1) {
            showDialog(getString(R.string.please_select_payment_type));
            return;
        }
        listener.gotoChangeAddress();
    }
}