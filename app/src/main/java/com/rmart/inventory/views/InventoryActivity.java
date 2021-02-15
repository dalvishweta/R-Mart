package com.rmart.inventory.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.inventory.models.APIUnitMeasures;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.viewmodel.InventoryViewModel;
import com.rmart.retiler.inventory.product_from_inventory.activities.ProductFromInvetoryList;
import com.rmart.retiler.product.OnUnitSaveListner;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.pojos.APIStockListResponse;
import com.rmart.utilits.pojos.APIStockResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.services.APIService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryActivity extends BaseNavigationDrawerActivity implements OnInventoryClickedListener {

    InventoryViewModel inventoryViewModel;
    private APIStockListResponse apiStockListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        getStockList();
        addFragment(ProductFromInvetoryList.newInstance(), ProductFromInvetoryList.class.getName(), false);
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    @Override
    public void showMyCategories() {
        addFragment(MyCategoryListFragment.newInstance("",""), MyCategoryListFragment.class.getName(), true);
    }

    @Override
    public void showMySubCategories() {
        replaceFragment(MySubCategoriesListFragment.newInstance("",""), MySubCategoriesListFragment.class.getName(),true);
    }

    @Override
    public void showMyProducts() {
        replaceFragment(ProductFromInvetoryList.newInstance(), ProductFromInvetoryList.class.getName(),true);
    }

    @Override
    public void showProductPreview(ProductResponse product, boolean isEdit) {

        replaceFragment(ShowProductPreviewFragment.newInstance(product,isEdit, apiStockListResponse), ShowProductPreviewFragment.class.getName(),true);
    }

    @Override
    public void updateProduct(ProductResponse product, boolean isEdit) {
        replaceFragment(AddProductToInventory.newInstance(product,isEdit, this.apiStockListResponse), AddProductToInventory.class.getName(),true);
    }

    @Override
    public void addProductToInventory(String listType, String id) {
        replaceFragment(SelectProductFromInventory.newInstance(listType, id), SelectProductFromInventory.class.getName(),true);
    }

    @Override
    public void requestToCreateProduct() {
        replaceFragment(AddProductToAPI.newInstance(), AddProductToAPI.class.getName(),true);
    }

    @Override
    public void requestNewBrand() {

    }

    @Override
    public void addUnit(UnitObject unitValue, APIUnitMeasures unitMeasurements, BaseInventoryFragment fragment, int requestID,int unit_for) {
        FragmentManager fm = getSupportFragmentManager();
        AddUnitDialog addUnitDialog = AddUnitDialog.newInstance(unitValue, false, apiStockListResponse,
                unitMeasurements, unit_for,new OnUnitSaveListner() {
                    @Override
                    public void onSaveUnit(int requestCode, Intent intent) {

                    }
                });
        addUnitDialog.setCancelable(false);
        addUnitDialog.setTargetFragment(fragment, requestID);
        addUnitDialog.show(fm, AddUnitDialog.class.getName());
    }

    @Override
    public void goToHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        addFragment(ProductFromInvetoryList.newInstance(), "MyProductsListFragment", false);
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
        getToActivity(view.getId(), view.getId() == R.id.retailer_inventory);
    }

    public void getStockList() {
        APIService apiService = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        apiService.getAPIStockList().enqueue(new Callback<APIStockListResponse>() {
            @Override
            public void onResponse(@NotNull Call<APIStockListResponse> call, @NotNull Response<APIStockListResponse> response) {
                if(response.isSuccessful()) {
                    apiStockListResponse = response.body();
                    assert apiStockListResponse != null;
                    for (APIStockResponse apiStockResponse: apiStockListResponse.getArrayList()) {
                        if(apiStockResponse.getStockID().equalsIgnoreCase("5")) {
                            apiStockListResponse.getArrayList().remove(apiStockResponse);
                            apiStockListResponse.getArrayList().add(0, apiStockResponse);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<APIStockListResponse> call, @NotNull Throwable t) {

            }
        });
    }


    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }
}