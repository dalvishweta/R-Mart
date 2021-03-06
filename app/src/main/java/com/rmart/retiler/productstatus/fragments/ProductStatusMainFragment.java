package com.rmart.retiler.productstatus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rmart.R;
import com.rmart.databinding.ActivityProductlistFromInventoryRetailerForTabBinding;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.profile.model.MyProfile;
import com.rmart.retiler.inventory.brand.activities.BrandFilterActivity;
import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.retiler.inventory.category.activities.CategoryFilterActivity;
import com.rmart.retiler.inventory.category.model.Category;
import com.rmart.retiler.inventory.product_from_inventory.viewmodel.ProductFromInventoryViewModel;
import com.rmart.retiler.inventory.product_from_library.activities.ProductList;
import com.rmart.retiler.productstatus.adapters.ProductFromInventorySearchListAdapter;
import com.rmart.retiler.productstatus.adapters.ProductPagerAdapter;
import com.rmart.utilits.Utils;

import java.util.ArrayList;

public class ProductStatusMainFragment extends BaseInventoryFragment {
    ProductFromInventorySearchListAdapter productSearchListAdapter;
    final int CATEGORY_REQUEST=2;
    final int BRAND_REQUEST=3;
    int page=0;
    ActivityProductlistFromInventoryRetailerForTabBinding binding;
    ProductFromInventoryViewModel productViewModel;
    private ProductPagerAdapter viewPagerAdapter;
    public ProductStatusMainFragment() {
    }
    public static ProductStatusMainFragment newInstance() {
        ProductStatusMainFragment fragment = new ProductStatusMainFragment();

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
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_productlist_from_inventory_retailer_for_tab, container, false);
        productViewModel.getProductList( page+"", MyProfile.getInstance(getContext()).getUserID(),getContext());
        binding.setProductViewModel(productViewModel);
        binding.setLifecycleOwner(this);
        viewPagerAdapter = new ProductPagerAdapter(getChildFragmentManager(),0);
        binding.productPager.setAdapter(viewPagerAdapter);
        binding.productStatusTabs.setupWithViewPager(binding.productPager);
        binding.filter.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(requireActivity(), binding.filter);
            popup.getMenu().add(Menu.NONE, 4, 4, Utils.CATEGORY);
            popup.getMenu().add(Menu.NONE, 3, 3, Utils.BRAND);
            popup.getMenu().add(Menu.NONE, 5, 5, "Clear Filter");
            popup.show();
            popup.setOnMenuItemClickListener(item -> {

                if(item.getTitle().toString().equalsIgnoreCase(Utils.CATEGORY)){
                    Intent intent=new Intent(getContext(), CategoryFilterActivity.class);
                    MyProfile profile = MyProfile.getInstance(getContext());
                    intent.putExtra("venderID", MyProfile.getInstance(getContext()).getUserID());
                    startActivityForResult(intent, CATEGORY_REQUEST);
                }
                if(item.getTitle().toString().equalsIgnoreCase(Utils.BRAND)) {
                    Intent intent=new Intent(getContext(), BrandFilterActivity.class);
                    intent.putExtra("venderID", MyProfile.getInstance(getContext()).getUserID());
                    startActivityForResult(intent, BRAND_REQUEST);

                }
                if(item.getTitle().toString().equalsIgnoreCase("Clear Filter")){
                    productViewModel.categoryID.setValue(null);
                    productViewModel.brandID.setValue(null);
                    productSearchListAdapter.products.clear();
                    productSearchListAdapter.notifyDataSetChanged();
                }

                return true;
            });
        });
        binding.edtProductSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String text = charSequence.toString();
                if(text!=null && text.length()>0) {

                    productViewModel.searchPhrase.setValue(text);
                    viewPagerAdapter.onTextChange(text);

                } else {
                    productViewModel.searchPhrase.setValue(null);
                    viewPagerAdapter.onTextChange(null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ivSearchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.filterField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(requireActivity(), binding.filterField);
                popup.getMenu().add(Menu.NONE, 4, 4, Utils.CATEGORY);
                popup.getMenu().add(Menu.NONE, 3, 3, Utils.BRAND);
                popup.getMenu().add(Menu.NONE, 5, 5, "Clear Filter");
                popup.show();
                popup.setOnMenuItemClickListener(item -> {

                    if(item.getTitle().toString().equalsIgnoreCase(Utils.CATEGORY)){
                        Intent intent=new Intent(getContext(), CategoryFilterActivity.class);
                        startActivityForResult(intent, CATEGORY_REQUEST);
                    }
                    if(item.getTitle().toString().equalsIgnoreCase(Utils.BRAND)) {
                        Intent intent=new Intent(getContext(), BrandFilterActivity.class);
                        startActivityForResult(intent, BRAND_REQUEST);

                    }
                    if(item.getTitle().toString().equalsIgnoreCase("Clear Filter")){
                        productViewModel.categoryID.setValue(null);
                        productViewModel.brandID.setValue(null);
                        productSearchListAdapter.products.clear();
                        productSearchListAdapter.notifyDataSetChanged();
                    }

                    return true;
                });
            }
        });

        binding.addCustomProduct.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.base_container, ProductList.newInstance(), ProductList.class.getName());
            fragmentTransaction.addToBackStack( ProductList.class.getName());
            fragmentTransaction.commit();
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
       }


    }
}