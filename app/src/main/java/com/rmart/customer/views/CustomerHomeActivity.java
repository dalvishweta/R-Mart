package com.rmart.customer.views;

import android.os.Bundle;
import android.view.View;

import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;
import com.rmart.customer.models.CustomerProductsModel;
import com.rmart.customer.models.VendorProductDataResponse;
import com.rmart.customer.models.VendorProductDetailsResponse;

public class CustomerHomeActivity extends BaseNavigationDrawerActivity implements OnCustomerHomeInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_authenticatin);

        replaceFragment(VendorListViewFragment.getInstance(),"VendorListViewFragment",false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void gotoChangeAddress() {
        addFragment(ChangeAddressFragment.getInstance(),"ChangeAddressFragment",false);
    }

    @Override
    public void gotoVendorProductDetails(CustomerProductsModel customerProductsModel) {
        addFragment(VendorProductDetailsFragment.getInstance(customerProductsModel),"VendorProductDetailsFragment",false);
    }

    @Override
    public void gotoProductDescDetails(VendorProductDataResponse vendorProductDataDetails) {
        addFragment(ProductCartDetailsFragment.getInstance(vendorProductDataDetails),"ProductCartDetailsFragment",false);
    }

    @Override
    public void gotoPaymentScreen() {
        addFragment(MakePaymentFragment.getInstance("", ""),"MakePaymentFragment",false);
    }
}
