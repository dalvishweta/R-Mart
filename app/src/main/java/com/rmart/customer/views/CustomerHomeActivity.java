package com.rmart.customer.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer.OnCustomerHomeInteractionListener;

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
}
