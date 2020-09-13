package com.rmart.orders.views;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.orders.OnOrdersInteractionListener;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.viewmodel.MyOrdersViewModel;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.Utils;


public class OrdersActivity extends BaseNavigationDrawerActivity implements OnOrdersInteractionListener {

    MyOrdersViewModel myOrdersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOrdersViewModel = new ViewModelProvider(this).get(MyOrdersViewModel.class);
        try {
            if (MyProfile.getInstance().getRoleID().equals(Utils.DELIVERY_ID)) {
                myOrdersViewModel.getSelectedOrderGroup().setValue(myOrdersViewModel.getShippedOrders().getValue());
                addFragment(OrderListFragment.newInstance(""), "OrderListFragment", false);
            } else if (MyProfile.getInstance().getRoleID().equals(Utils.RETAILER_ID)) {
                addFragment(OrderHomeFragment.newInstance("", ""), "OrderHomeFragment", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goToHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void goToViewFullOrder(OrderObject orderObject) {
        hideHamburgerIcon();
        replaceFragment(ViewFullOrderFragment.newInstance(orderObject, ""), "ViewFullOrderFragment", true);
    }

    @Override
    public void goToOTPValidation(OrderObject orderObject) {
        hideHamburgerIcon();
        replaceFragment(CustomerVerificationFragment.newInstance(orderObject, ""), "CustomerVerificationFragment", true);
    }

    @Override
    public void goToProcessToDelivery(OrderObject orderObject) {
        hideHamburgerIcon();
        replaceFragment(ProcessToDeliveryFragment.newInstance(orderObject, ""), "ProcessToDeliveryFragment", true);
    }

    @Override
    public void showOrderList() {
        hideHamburgerIcon();
        replaceFragment(OrderListFragment.newInstance(""), "OrderListFragment", true);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.orders) {
            getToActivity(view.getId(), false);
        } else {
            getToActivity(view.getId(), true);
        }
    }
}