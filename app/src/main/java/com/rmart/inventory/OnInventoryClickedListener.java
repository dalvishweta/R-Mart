package com.rmart.inventory;

import com.rmart.inventory.models.Product;
import com.rmart.inventory.models.UnitObject;
import com.rmart.inventory.views.BaseInventoryFragment;

public interface OnInventoryClickedListener {
    void goToRequestNewProduct();
    void showMyCategories();
    void showMySubCategories();
    void goToSelectDataFragment();
    void showMyProducts();
    void showProductPreview(Product tag);
    void updateProduct(Product product);
    void requestNewProduct();
    void requestNewBrand();
    void addUnit(UnitObject unitValue, BaseInventoryFragment fragment, int requestID);
    void goToHome();
}
