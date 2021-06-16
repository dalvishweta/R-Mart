package com.rmart.inventory.filter.brand.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rmart.R;

import com.rmart.databinding.ProductBrandFilterLayoutBinding;
import com.rmart.inventory.filter.brand.adapter.ProductBrandAdapter;
import com.rmart.inventory.filter.brand.models.ProductBrandReponse;
import com.rmart.inventory.filter.brand.viewmodels.ProductBrandServicemodule;
import com.rmart.utilits.GridSpacesItemDecoration;

public class ProductBrandFilter extends AppCompatActivity {
    ProductBrandFilterLayoutBinding binding;
    private String brandID = null;
    String vendor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brand_filter);
        vendor_id = getIntent().getStringExtra("venderID");
        System.out.println("DATAFG"+getIntent().getStringExtra("venderID"));
        binding = DataBindingUtil.setContentView(this, R.layout.product_brand_filter_layout);
        ProductBrandServicemodule brandViewModel = ViewModelProviders.of(this).get(ProductBrandServicemodule.class);
        brandViewModel.getBransList(null, vendor_id);
        binding.setProductbrandViewModel(brandViewModel);
        binding.setLifecycleOwner(this);
        binding.btnTryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brandViewModel.getBransList(null, vendor_id);
            }
        });
        binding.rvBrands.addItemDecoration(new GridSpacesItemDecoration(15));
        brandViewModel.brandListResponseMutableLiveData.observeForever(new Observer<ProductBrandReponse>() {
            @Override
            public void onChanged(ProductBrandReponse brandListResponse) {
                binding.rvBrands.setAdapter(new ProductBrandAdapter(ProductBrandFilter.this, brandListResponse.getBrand(), brand -> {


                    Intent _result = new Intent();
                    _result.putExtra("data", brand);
                    setResult(Activity.RESULT_OK, _result);
                    finish();

                }));

            }
        });
    }
}