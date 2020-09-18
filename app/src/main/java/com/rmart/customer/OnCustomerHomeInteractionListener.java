package com.rmart.customer;

import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.customer.models.CustomerProductDetailsModel;

public interface OnCustomerHomeInteractionListener {

    void gotoChangeAddress();

    void gotoVendorProductDetails(CustomerProductsShopDetailsModel customerProductsModel);

    void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDataDetails, CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoPaymentScreen();

    void gotoShoppingCartScreen();

    void gotoSelectedShopDetails(ShoppingCartResponseDetails shoppingCartResponseDetails);

    void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, CustomerProductsShopDetailsModel vendorShopDetails);
}
