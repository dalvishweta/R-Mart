package com.rmart.inventory.views;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.viewmodel.InventoryViewModel;
import com.rmart.utilits.pojos.ProductResponse;

public class InventoryActivity extends BaseNavigationDrawerActivity implements OnInventoryClickedListener {
    InventoryViewModel inventoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        addFragment(MyProductsListFragment.newInstance("", ""), "MyProductsListFragment", false);
        /*if (inventoryViewModel.getIsProductView().getValue().equals(InventoryViewModel.PRODUCT)) {
            addFragment(MyProductsListFragment.newInstance("", ""), "MyProductsListFragment", false);
        } else if (inventoryViewModel.getIsProductView().getValue().equals(InventoryViewModel.SUB_CATEGORY)) {
            addFragment(MySubCategoriesListFragment.newInstance("", ""), "MyProductsListFragment", false);
        } else {
            addFragment(MyCategoryListFragment.newInstance("", ""), "MyCategoryFragment", false);
        }*/
    }

    @Override
    public void showMyCategories() {
        addFragment(MyCategoryListFragment.newInstance("",""), "MyCategoryFragment", true);
    }

    @Override
    public void showMySubCategories() {
        replaceFragment(MySubCategoriesListFragment.newInstance("",""), "MySubCategoriesFragment",true);
    }

    @Override
    public void showMyProducts() {
        replaceFragment(MyProductsListFragment.newInstance("",""), "MyProductsListFragment",true);
    }

    @Override
    public void showProductPreview(Product product, boolean isEdit) {
        replaceFragment(ShowProductPreviewFragment.newInstance(product,isEdit), "ShowProductPreviewFragment",true);
    }

    @Override
    public void updateProduct(ProductResponse product, boolean isEdit) {
        replaceFragment(AddProductToInventory.newInstance(product,isEdit), "UploadProductFragment",true);
    }

    @Override
    public void addProductToInventory() {
        replaceFragment(SelectProductFromInventory.newInstance("",""), "AddProductToInventory",true);
    }

    @Override
    public void requestToCreateProduct() {
        replaceFragment(AddProductToAPI.newInstance("",""), "AddProductInAPI",true);
    }

    @Override
    public void requestNewBrand() {

    }

    @Override
    public void addUnit(UnitObject unitValue, BaseInventoryFragment fragment, int requestID) {
        FragmentManager fm = getSupportFragmentManager();
        AddUnitDialog addUnitDialog = AddUnitDialog.newInstance(unitValue,false);
        addUnitDialog.setCancelable(false);
        addUnitDialog.setTargetFragment(fragment, requestID);
        addUnitDialog.show(fm, "AddUnitDialog");
    }

    @Override
    public void goToHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (inventoryViewModel.getIsProductView().getValue().equals(InventoryViewModel.PRODUCT)) {
            addFragment(MyProductsListFragment.newInstance("", ""), "MyProductsListFragment", false);
        } else if (inventoryViewModel.getIsProductView().getValue().equals(InventoryViewModel.SUB_CATEGORY)) {
            addFragment(MySubCategoriesListFragment.newInstance("", ""), "MyProductsListFragment", false);
        } else {
            addFragment(MyCategoryListFragment.newInstance("", ""), "MyCategoryFragment", false);
        }
    }

    @Override
    public void requestNewProduct(BaseInventoryFragment fragment, int requestID) {

    }

    @Override
    public void applyFilter(BaseInventoryFragment fragment, int requestID) {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance( "", "");
        filterFragment.setCancelable(true);
        filterFragment.setTargetFragment(fragment, requestID);
        filterFragment.show(fm, "FilterFragment");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.inventory) {
            getToActivity(view.getId(), false);
        } else {
            getToActivity(view.getId(), true);
        }
    }
}