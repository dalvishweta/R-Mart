package com.rmart.retiler.inventory.category.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.databinding.ActivityCategoryFilterBinding;
import com.rmart.retiler.inventory.category.adapters.CategoryListAdapter;
import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.retiler.inventory.category.model.CategoryListResponce;
import com.rmart.retiler.inventory.category.viewmodels.CategoryViewModel;
import com.rmart.utilits.GridSpacesItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CategoryFilterActivity extends AppCompatActivity {
    ActivityCategoryFilterBinding binding;
    private Category categoryID = null;
    CategoryViewModel categoryViewModel;
    String vendor_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brand_filter);

        vendor_id =getIntent().getStringExtra("venderID");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_category_filter);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList(null,vendor_id);
        binding.setCategoryViewModel(categoryViewModel);
        binding.setLifecycleOwner(this);
        binding.btnTryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryViewModel.getCategoryList(null,vendor_id);
            }
        });
        binding.rvBrands.addItemDecoration(new GridSpacesItemDecoration(15));
        categoryViewModel.categoryListResponceMutableLiveData.observeForever(new Observer<CategoryListResponce>() {
            @Override
            public void onChanged(CategoryListResponce categoryListResponce) {
                binding.rvBrands.setAdapter(new CategoryListAdapter(CategoryFilterActivity.this, categoryListResponce.getCategories(), category -> {

                        if(categoryID!=null) {
                            Intent _result = new Intent();
                            _result.putExtra("data", category);
                            setResult(Activity.RESULT_OK, _result);
                            finish();
                        } else {
                            categoryID = category;
                            categoryViewModel.getCategoryList(category.getId()+"",vendor_id);
                        }

                }));

            }
        });




    }

    @Override
    public void onBackPressed() {

        if(categoryID!=null){
            categoryID=null;
            categoryViewModel.getCategoryList(null,vendor_id);

        } else {
            super.onBackPressed();
        }

    }
}