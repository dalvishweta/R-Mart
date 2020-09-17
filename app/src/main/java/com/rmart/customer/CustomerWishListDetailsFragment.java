package com.rmart.customer;

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

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListDetailsFragment extends BaseFragment {

    private RecyclerView productsListField;
    private TextView tvShopNameField;
    private TextView tvNoOfProductsField;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        productsListField = view.findViewById(R.id.products_list_field);
        tvShopNameField = view.findViewById(R.id.tv_shop_name_field);
        tvNoOfProductsField = view.findViewById(R.id.tv_no_of_products_field);

        productsListField.setHasFixedSize(false);

        GridLayoutManager manager = new GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false);
        productsListField.setLayoutManager(manager);


    }
}
