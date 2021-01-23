package com.rmart.retiler.inventory.product_from_library.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rmart.R;
import com.rmart.databinding.ActivityProductlistRetailerBinding;
import com.rmart.retiler.inventory.product_from_library.model.ProductListResponse;
import com.rmart.retiler.inventory.product_from_library.viewmodel.ProductViewModel;
import com.rmart.utilits.GridSpacesItemDecoration;

public class ProductListActivity extends AppCompatActivity {
    ActivityProductlistRetailerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brand_filter);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_productlist_retailer);
        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getProductList("0");
        binding.setProductViewModel(productViewModel);
        binding.setLifecycleOwner(this);
        binding.rvBrands.addItemDecoration(new GridSpacesItemDecoration(15));



        //
        productViewModel.productListResponseMutableLiveData.observeForever(new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
             //   binding.rvBrands.setAdapter(new ProductSearchListAdapter(ProductListActivity.this, productListResponse.getProduct()));

            }
        });

    }
}