package com.rmart.inventory;

import com.rmart.inventory.models.APIUnitMeasures;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.utilits.pojos.ProductResponse;

public interface OnInventoryClickedListener {
    void showMyCategories();
    void requestNewBrand();
    void showMySubCategories();
    void showMyProducts();
    void showProductPreview(ProductResponse tag, boolean isEdit);
    void updateProduct(ProductResponse product, boolean isEdit);
    void addProductToInventory(String listType, String id);
    void requestToCreateProduct();
    void addUnit(UnitObject unitValue, APIUnitMeasures unitMeasurements, BaseInventoryFragment fragment, int requestID);
    void goToHome();
    void requestNewProduct(BaseInventoryFragment fragment, int requestID);
    void applyFilter(BaseInventoryFragment fragment, int requestFilteredDataId);
}
