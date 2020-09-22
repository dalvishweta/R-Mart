package com.rmart.customer;

import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.customer.models.ShoppingCartResponseDetails;

public interface OnCustomerHomeInteractionListener {

    void gotoChangeAddress();

    void gotoVendorProductDetails(CustomerProductsShopDetailsModel customerProductsModel);

    void gotoProductDescDetails(CustomerProductDetailsModel vendorProductDataDetails, CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoPaymentOptionsScreen(CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoShoppingCartScreen();

    void gotoShoppingCartDetails(ShoppingCartResponseDetails shoppingCartResponseDetails);

    void gotoVendorSameProductListScreen(ProductBaseModel productCategoryDetails, CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoConfirmedOrderStatusScreen();

    void updateShopWishListStatus(CustomerProductsShopDetailsModel vendorShopDetails);

    void gotoCompleteOrderDetailsScreen(CustomerProductsShopDetailsModel vendorShopDetails);
}
