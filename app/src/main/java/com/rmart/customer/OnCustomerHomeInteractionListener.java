package com.rmart.customer;

import com.rmart.customer.models.CustomerProductsModel;
import com.rmart.customer.models.VendorProductDataResponse;
import com.rmart.customer.models.VendorProductDetailsResponse;
import com.rmart.customer.models.VendorProductShopDataResponse;

public interface OnCustomerHomeInteractionListener {

    void gotoChangeAddress();

    void gotoVendorProductDetails(CustomerProductsModel customerProductsModel);

    void gotoProductDescDetails(VendorProductDataResponse vendorProductDataDetails, CustomerProductsModel vendorShopDetails);

    void gotoPaymentScreen();
}
