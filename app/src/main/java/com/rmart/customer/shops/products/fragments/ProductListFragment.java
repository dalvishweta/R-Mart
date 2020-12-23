package com.rmart.customer.shops.products.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.viewmodel.ShopHomeViewModel;
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.databinding.FragmentProductListBinding;
import com.rmart.databinding.FragmentShopHomePageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {

    private CustomerProductsShopDetailsModel productsShopDetailsModel;
    private static final String ARG_SHOP = "shop_details";
    private static final String CATEGOERY = "categoery";

    public ProductListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProductListFragment newInstance(CustomerProductsShopDetailsModel customerProductsShopDetailsModel, Category category) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SHOP, customerProductsShopDetailsModel);
        args.putSerializable(CATEGOERY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productsShopDetailsModel = (CustomerProductsShopDetailsModel) getArguments().getSerializable(ARG_SHOP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentProductListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false);
        binding.setLifecycleOwner(this);

      return   binding.getRoot();
    }
}