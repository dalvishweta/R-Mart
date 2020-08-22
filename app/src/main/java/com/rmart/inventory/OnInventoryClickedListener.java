package com.rmart.inventory;

import com.rmart.inventory.models.Product;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.views.BaseInventoryFragment;

public interface OnInventoryClickedListener {
    void showMyCategories();
    void requestNewBrand();
    void showMySubCategories();
    void showMyProducts();
    void showProductPreview(Product tag, boolean isEdit);
    void updateProduct(Product product, boolean isEdit);
    void addNewProduct();
    void addUnit(UnitObject unitValue, BaseInventoryFragment fragment, int requestID);
    void goToHome();
}
