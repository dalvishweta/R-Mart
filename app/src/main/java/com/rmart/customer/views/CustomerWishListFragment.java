package com.rmart.customer.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.OnCustomerWishListInteractionListener;
import com.rmart.customer.adapters.WishListAdapter;
import com.rmart.customer.models.WishListResponseDetails;
import com.rmart.customer.models.WishListResponseModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RecyclerTouchListener;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Satya Seshu on 14/09/20.
 */
public class CustomerWishListFragment extends BaseFragment {

    private RecyclerView wishListField;
    private List<WishListResponseDetails> wishListCart;
    private OnCustomerWishListInteractionListener onCustomerWishListInteractionListener;

    public static CustomerWishListFragment getInstance() {
        CustomerWishListFragment customerWishListFragment = new CustomerWishListFragment();
        Bundle extras = new Bundle();
        customerWishListFragment.setArguments(extras);
        return customerWishListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LoggerInfo.printLog("Fragment", "CustomerWishListFragment");
        return inflater.inflate(R.layout.fragment_customer_wish_list, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCustomerWishListInteractionListener) {
            onCustomerWishListInteractionListener = (OnCustomerWishListInteractionListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.wish_list));
        getWishListData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        wishListField = view.findViewById(R.id.wish_list_field);
        wishListField.setHasFixedSize(false);

        wishListCart = new ArrayList<>();

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.recycler_decoration_divider_transparent)));
        wishListField.addItemDecoration(divider);

        view.findViewById(R.id.btn_proceed_to_buy_field).setOnClickListener(v -> proceedToBuySelected());

        wishListField.addOnItemTouchListener(new RecyclerTouchListener(requireActivity(), "", wishListField, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                WishListResponseDetails selectedWishListDetails = wishListCart.get(position);
                shopDetailsSelected(selectedWishListDetails.getVendorId());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getWishListData() {
        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            wishListCart.clear();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<WishListResponseModel> call = customerProductsService.getShowWishListData(clientID, MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<WishListResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<WishListResponseModel> call, @NotNull Response<WishListResponseModel> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        WishListResponseModel body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<WishListResponseDetails> shopWiseCartList = body.getWishListResponseDataResponse().getWishListResponseDetails();
                                if (shopWiseCartList != null && !shopWiseCartList.isEmpty()) {
                                    wishListCart.addAll(shopWiseCartList);
                                    setAdapter(body.getMsg());
                                } else {
                                    showCloseDialog(null, body.getMsg());
                                }
                            } else {
                                showCloseDialog(null, body.getMsg());
                            }
                        } else {
                            showCloseDialog(null, getString(R.string.no_information_available));
                        }
                    } else {
                        showCloseDialog(null, getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<WishListResponseModel> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    showCloseDialog(null, t.getMessage());
                }
            });
        } else {
            showCloseDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
        }
    }

    private void showCloseDialog(String title, String message) {
        showDialog(title, message, pObject -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void shopDetailsSelected(int vendorId) {
        onCustomerWishListInteractionListener.gotoWishListDetailsScreen(vendorId);
    }

    private void proceedToBuySelected() {

    }

    private void setAdapter(String message) {
        if (!wishListCart.isEmpty()) {
            WishListAdapter wishListAdapter = new WishListAdapter(requireActivity(), wishListCart);
            wishListField.setAdapter(wishListAdapter);
        } else {
            showCloseDialog(null, message);
        }
    }
}
