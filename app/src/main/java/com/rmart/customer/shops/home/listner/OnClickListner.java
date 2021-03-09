package com.rmart.customer.shops.home.listner;

import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;

public interface OnClickListner {
    public void onCategorySelected(Category category);
    public void onViewSelected(String viewmoreType);
    public void onProductSelected(ProductData productData);
}