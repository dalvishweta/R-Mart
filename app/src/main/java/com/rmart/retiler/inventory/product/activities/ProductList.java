package com.rmart.retiler.inventory.product.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rmart.R;
import com.rmart.databinding.ActivityProductlistRetailerBinding;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.inventory.views.MyProductsListFragment;
import com.rmart.retiler.inventory.product.adapters.ProductSearchListAdapter;
import com.rmart.retiler.inventory.product.model.ProductListResponse;
import com.rmart.retiler.inventory.product.viewmodel.ProductViewModel;
import com.rmart.utilits.GridSpacesItemDecoration;

public class ProductList extends BaseInventoryFragment {

    ActivityProductlistRetailerBinding binding;


    public ProductList() {
        // Required empty public constructor
    }

    public static ProductList newInstance(String param1, String param2) {
        ProductList fragment = new ProductList();

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.my_product_list));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_productlist_retailer);
        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getProductList("19", "338", "onion", "0");
        binding.setProductViewModel(productViewModel);
        binding.setLifecycleOwner(this);
        binding.rvBrands.addItemDecoration(new GridSpacesItemDecoration(15));


        //
        productViewModel.productListResponseMutableLiveData.observeForever(new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                binding.rvBrands.setAdapter(new ProductSearchListAdapter(getActivity(), productListResponse.getProduct()));

            }
        });

        return null;
    }

}
