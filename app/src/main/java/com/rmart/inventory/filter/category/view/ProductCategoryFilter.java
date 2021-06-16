package com.rmart.inventory.filter.category.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rmart.R;
import com.rmart.databinding.ProductCategoryFilterLayoutBinding;
import com.rmart.inventory.filter.category.adapter.ProductCategoryAdapter;
import com.rmart.inventory.filter.category.models.ProductCategoryResponse;
import com.rmart.inventory.filter.category.viewmodels.ProductCategoryServicemodule;

import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.utilits.GridSpacesItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProductCategoryFilter extends AppCompatActivity {
    ProductCategoryFilterLayoutBinding binding;
    private Category categoryID = null;
    ProductCategoryServicemodule categoryViewModel;
    String vendor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brand_filter);

        vendor_id = getIntent().getStringExtra("venderID");

        binding = DataBindingUtil.setContentView(this, R.layout.product_category_filter_layout);
        categoryViewModel = ViewModelProviders.of(this).get(ProductCategoryServicemodule.class);
        categoryViewModel.getCategoryList(null, vendor_id);
        //System.out.println(vendor_id);
        binding.setCategoryViewModel(categoryViewModel);
        binding.setLifecycleOwner(this);
        binding.btnTryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryViewModel.getCategoryList(null, vendor_id);
            }
        });
        binding.rvBrands.addItemDecoration(new GridSpacesItemDecoration(15));
        categoryViewModel.categoryListResponceMutableLiveData.observeForever(new Observer<ProductCategoryResponse>() {
            @Override
            public void onChanged(ProductCategoryResponse categoryListResponce) {
                binding.rvBrands.setAdapter(new ProductCategoryAdapter(ProductCategoryFilter.this, categoryListResponce.getCategories(), category -> {

                    if (categoryID != null) {
                        Intent _result = new Intent();
                        _result.putExtra("data", category);
                        setResult(Activity.RESULT_OK, _result);
                        finish();
                    } else {
                        categoryID = category;
                        categoryViewModel.getCategoryList(category.getId() + "", vendor_id);
                    }

                }));

            }
        });


    }

    @Override
    public void onBackPressed() {

        if (categoryID != null) {
            categoryID = null;
            categoryViewModel.getCategoryList(null, vendor_id);

        } else {
            super.onBackPressed();
        }

    }
}
