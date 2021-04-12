package com.rmart.retiler.productstatus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.databinding.FragmentProductStatusBinding;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.retiler.inventory.product_from_inventory.model.Product;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductFromInventoryListResponse;
import com.rmart.retiler.inventory.product_from_inventory.viewmodel.ProductFromInventoryViewModel;
import com.rmart.retiler.productstatus.adapters.ProductFromInventorySearchListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductFromInvetoryList extends BaseInventoryFragment {
    ProductFromInventorySearchListAdapter productSearchListAdapter;
    final int CATEGORY_REQUEST=2;
    final int BRAND_REQUEST=3;
    int page=0;
    int total_product_count= 0;
    FragmentProductStatusBinding binding;
    ProductFromInventoryViewModel productViewModel;

    public ProductFromInvetoryList() {
        // Required empty public constructor
    }

    public static ProductFromInvetoryList newInstance() {
        ProductFromInvetoryList fragment = new ProductFromInvetoryList();

        return fragment;
    }
    public static BaseInventoryFragment newInstance(List<Product> sm) {
        ProductFromInvetoryList fragment = new ProductFromInvetoryList();
        Bundle args = new Bundle();
        args.putSerializable("sm", (Serializable) sm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.my_product_list_from_library));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        productViewModel = ViewModelProviders.of(this).get(ProductFromInventoryViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_status, container, false);


        productViewModel.getProductList( page+"");
        binding.setProductViewModel(productViewModel);
        binding.setLifecycleOwner(this);
       // binding.productList.addItemDecoration(new GridSpacesItemDecoration(15));

        productSearchListAdapter = new ProductFromInventorySearchListAdapter(getActivity(),new ArrayList<>(),mListener);
        binding.productList.setAdapter(productSearchListAdapter);




        productViewModel.productListResponseMutableLiveData.observeForever(new Observer<ProductFromInventoryListResponse>() {
            @Override
            public void onChanged(ProductFromInventoryListResponse productListResponse) {
                try {
                    if(page==0) {
                        productSearchListAdapter.products.clear();
                        productSearchListAdapter.notifyDataSetChanged();
                    }
                    productSearchListAdapter.addProducts(productListResponse.getProduct());
                    total_product_count = productListResponse.getTotal_count();

                } catch (Exception e){

                }
            }
        });






        binding.productList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!productViewModel.isLoading.getValue()) {
                    if (productViewModel.productListResponseMutableLiveData.getValue().isNext_value()) {
                        page++;
                        productViewModel.getProductList( page+"");

                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(Activity.RESULT_OK==resultCode) {
           if (requestCode == CATEGORY_REQUEST) {
             Category category = (Category) data.getExtras().getSerializable("data");
             productViewModel.categoryID.setValue(category);
           }
           if (requestCode == BRAND_REQUEST) {
               Brand  brand= (Brand) data.getExtras().getSerializable("data");
               productViewModel.brandID.setValue(brand);

           }
           page=0;

           productViewModel.getProductList( page+"");
       }


    }
}
