package com.rmart.inventory.views;

import android.os.Bundle;

import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.inventory.OnInventoryClickedListener;

public class InventoryActivity extends BaseNavigationDrawerActivity implements OnInventoryClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(MyCategoryListFragment.newInstance("",""), "MyCategoryFragment", false);
    }

    @Override
    public void goToRequestNewProduct() {

    }

    @Override
    public void showMyCategories() {
        addFragment(MyCategoryListFragment.newInstance("",""), "MyCategoryFragment", false);
    }

    @Override
    public void showMySubCategories() {
        replaceFragment(MySubCategoriesListFragment.newInstance("",""), "MySubCategoriesFragment",true);
    }

    @Override
    public void goToSelectDataFragment() {

    }

    @Override
    public void showMyProducts() {
        replaceFragment(MyProductsListFragment.newInstance("",""), "MyProductsListFragment",true);
    }

    @Override
    public void showProductPreview() {
        replaceFragment(ShowProductPreviewFragment.newInstance("",""), "ShowProductPreviewFragment",true);
    }

    @Override
    public void updateProduct() {

    }

    @Override
    public void requestNewProduct() {

    }

    @Override
    public void requestNewBrand() {

    }
}