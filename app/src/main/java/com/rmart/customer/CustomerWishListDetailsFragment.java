package com.rmart.customer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.adapters.CustomerWishListDetailsAdapter;
import com.rmart.customer.models.ShoppingCartResponse;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.profile.model.MyProfile;
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
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListDetailsFragment extends BaseFragment {

    private RecyclerView productsListField;
    private TextView tvShopNameField;
    private TextView tvNoOfProductsField;
    private List<ShoppingCartResponseDetails> wishListCart;
    private ShoppingCartResponseDetails selectedWishListDetails;
    private OnCustomerHomeInteractionListener onCustomerHomeInteractionListener;

    public static CustomerWishListDetailsFragment getInstance(int vendorId) {
        CustomerWishListDetailsFragment customerWishListDetailsFragment = new CustomerWishListDetailsFragment();
        Bundle extras = new Bundle();
        extras.putInt("VendorId", vendorId);
        customerWishListDetailsFragment.setArguments(extras);
        return customerWishListDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_customer_wish_list_details, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCustomerHomeInteractionListener) {
            onCustomerHomeInteractionListener = (OnCustomerHomeInteractionListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(requireActivity()).setTitle(getString(R.string.wish_list));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);

        if (Utils.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            CustomerProductsService customerProductsService = RetrofitClientInstance.getRetrofitInstance().create(CustomerProductsService.class);
            String clientID = "2";
            Call<ShoppingCartResponse> call = customerProductsService.getShoppingCartList(clientID, 192, MyProfile.getInstance().getUserID());
            call.enqueue(new Callback<ShoppingCartResponse>() {
                @Override
                public void onResponse(@NotNull Call<ShoppingCartResponse> call, @NotNull Response<ShoppingCartResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        ShoppingCartResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                List<ShoppingCartResponseDetails> shopWiseCartList = body.getShoppingCartResponse().getShopWiseCartDataList();
                                if (shopWiseCartList != null && !shopWiseCartList.isEmpty()) {
                                    wishListCart.addAll(shopWiseCartList);
                                    setAdapter();
                                } else {
                                    showDialog(body.getMsg());
                                }
                            } else {
                                showDialog(body.getMsg());
                            }
                        } else {
                            showDialog(getString(R.string.no_information_available));
                        }
                    }  else {
                        showDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ShoppingCartResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text), pObject -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }
    }

    private void loadUIComponents(View view) {
        productsListField = view.findViewById(R.id.products_list_field);
        tvShopNameField = view.findViewById(R.id.tv_shop_name_field);
        tvNoOfProductsField = view.findViewById(R.id.tv_no_of_products_field);

        productsListField.setHasFixedSize(false);

        wishListCart = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false);
        productsListField.setLayoutManager(manager);
    }

    private void setAdapter() {
        if(!wishListCart.isEmpty()) {
            CustomerWishListDetailsAdapter adapter = new CustomerWishListDetailsAdapter(requireActivity(), wishListCart)
        }
    }
}
