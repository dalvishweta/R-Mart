package com.rmart.customer.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductOrderedResponseDetails;
import com.rmart.customer.models.ProductOrderedResponseModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentOptionsFragment#getInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentOptionsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";

    private ImageView ivCashOnDeliveryImageField;
    private ImageView ivInternetBankingImageField;
    private ImageView ivMyWalletImageField;
    private int selectedPaymentType = -1;
    private CustomerProductsShopDetailsModel vendorShopDetails;

    public PaymentOptionsFragment() {
        // Required empty public constructor
    }

    public static PaymentOptionsFragment getInstance(CustomerProductsShopDetailsModel vendorShopDetails) {
        PaymentOptionsFragment fragment = new PaymentOptionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) vendorShopDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vendorShopDetails = (CustomerProductsShopDetailsModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "PaymentOptionsFragment");
        return inflater.inflate(R.layout.fragment_payment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        ivCashOnDeliveryImageField = view.findViewById(R.id.iv_cash_on_delivery_field);
        ivInternetBankingImageField = view.findViewById(R.id.iv_internet_banking_field);
        ivMyWalletImageField = view.findViewById(R.id.iv_my_wallet_field);

        view.findViewById(R.id.cash_on_delivery_layout_field).setOnClickListener(v -> cashOnDeliverySelected());
        view.findViewById(R.id.internet_banking_layout_field).setOnClickListener(v -> internetBankingSelected());
        view.findViewById(R.id.my_wallet_layout_field).setOnClickListener(v -> myWalletSelected());
        view.findViewById(R.id.btn_proceed_field).setOnClickListener(v -> proceedSelected());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    public void updateToolBar() {
        requireActivity().setTitle(getString(R.string.make_payment));
        ((CustomerHomeActivity) (requireActivity())).hideCartIcon();
    }

    private void cashOnDeliverySelected() {
        resetItems();
        selectedPaymentType = 1;
        ivCashOnDeliveryImageField.setImageResource(R.drawable.ic_checked);
    }

    private void internetBankingSelected() {
        resetItems();
        selectedPaymentType = 2;
        ivInternetBankingImageField.setImageResource(R.drawable.ic_checked);
    }

    private void myWalletSelected() {
        resetItems();
        selectedPaymentType = 3;
        ivMyWalletImageField.setImageResource(R.drawable.ic_checked);
    }

    private void resetItems() {
        ivCashOnDeliveryImageField.setImageResource(R.drawable.ic_un_checked);
        ivInternetBankingImageField.setImageResource(R.drawable.ic_un_checked);
        ivMyWalletImageField.setImageResource(R.drawable.ic_un_checked);
    }

    private void proceedSelected() {
        if (selectedPaymentType == -1) {
            showDialog(getString(R.string.please_select_payment_type));
            return;
        }
        //listener.gotoChangeAddress();
        if (selectedPaymentType == 1) {
            if (Utils.isNetworkConnected(requireActivity())) {
                progressDialog.show();
                CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
                String clientID = "2";
                MyProfile myProfile = MyProfile.getInstance();
                Call<ProductOrderedResponseModel> call = customerProductsService.savePlaceToOrder(clientID, vendorShopDetails.getVendorId(), myProfile.getPrimaryAddressId(),
                        myProfile.getUserID(), vendorShopDetails.getShopId(), selectedPaymentType);
                call.enqueue(new Callback<ProductOrderedResponseModel>() {
                    @Override
                    public void onResponse(@NotNull Call<ProductOrderedResponseModel> call, @NotNull Response<ProductOrderedResponseModel> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            ProductOrderedResponseModel body = response.body();
                            if (body != null) {
                                if (body.getStatus().equalsIgnoreCase("success")) {
                                    ProductOrderedResponseDetails productOrderedResponseDetails = body.getProductOrderedResponseDetails();
                                    showSuccessDialog(productOrderedResponseDetails.getOrderedMessage());
                                    MyProfile.getInstance().setCartCount(productOrderedResponseDetails.getTotalCartCount());
                                } else {
                                    showDialog(body.getMsg());
                                }
                            } else {
                                showDialog(getString(R.string.no_information_available));
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ProductOrderedResponseModel> call, @NotNull Throwable t) {
                        progressDialog.dismiss();
                        showDialog(t.getMessage());
                    }
                });
            } else {
                showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            }
        }
    }

    private void showSuccessDialog(String orderedMessage) {
        showDialog(orderedMessage, pObject -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            String name = fragmentManager.getBackStackEntryAt(0).getName();
            fragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });
    }
}