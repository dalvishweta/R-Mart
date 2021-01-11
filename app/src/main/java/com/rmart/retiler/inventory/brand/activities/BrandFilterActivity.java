package com.rmart.retiler.inventory.brand.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.rmart.R;
import com.rmart.customer.shops.home.viewmodel.ShopHomeViewModel;
import com.rmart.databinding.ActivityBrandFilterBinding;
import com.rmart.retiler.inventory.brand.adapters.BrandListAdapter;
import com.rmart.retiler.inventory.brand.model.BrandListResponse;
import com.rmart.retiler.inventory.brand.repositories.BrandListRepository;
import com.rmart.retiler.inventory.brand.viewmodel.BrandViewModel;

public class BrandFilterActivity extends AppCompatActivity {
    ActivityBrandFilterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brand_filter);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_brand_filter);
        BrandViewModel brandViewModel = ViewModelProviders.of(this).get(BrandViewModel.class);
        brandViewModel.getBransList();




        //
        brandViewModel.brandListResponseMutableLiveData.observeForever(new Observer<BrandListResponse>() {
            @Override
            public void onChanged(BrandListResponse brandListResponse) {
                binding.rvBrands.setAdapter(new BrandListAdapter(BrandFilterActivity.this,brandListResponse.getBrand()));

            }
        });

    }
}