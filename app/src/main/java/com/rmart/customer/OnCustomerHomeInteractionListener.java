package com.rmart.customer;

import com.rmart.customer.models.CustomerProductsModel;
import com.rmart.customer.models.VendorProductDataResponse;
import com.rmart.customer.models.VendorProductDetailsResponse;

public interface OnCustomerHomeInteractionListener {

    void gotoChangeAddress();

    void gotoVendorProductDetails(CustomerProductsModel customerProductsModel);

    void gotoProductDescDetails(VendorProductDataResponse vendorProductDataDetails);

    void gotoPaymentScreen();
}
