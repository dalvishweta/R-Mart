package com.rmart.orders.views;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.orders.OnOrdersInteractionListener;
import com.rmart.orders.models.MyOrdersViewModel;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.models.OrdersByType;

public class OrdersActivity extends BaseNavigationDrawerActivity implements OnOrdersInteractionListener {

    MyOrdersViewModel myOrdersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOrdersViewModel = new ViewModelProvider(this).get(MyOrdersViewModel.class);
        addFragment(OrderHomeFragment.newInstance("", ""), "OrderHomeFragment", false);
    }

    @Override
    public void goToHome() {
       replaceFragment(OrderHomeFragment.newInstance("", ""), "OrderHomeFragment", true);
    }

//    @Override
//    public void goToOpenOrders() {
//        replaceFragment(OpenOrdersFragment.newInstance("", ""), "OpenOrdersFragment", false);
//    }
//
//    @Override
//    public void goToAcceptedOrders() {
//        replaceFragment(AcceptedOrdersFragment.newInstance("", ""), "AcceptedOrdersFragment", false);
//    }
//
//    @Override
//    public void goToPackedOrders() {
//        replaceFragment(PackedOrdersFragment.newInstance("", ""), "PackedOrdersFragment", false);
//    }
//
//    @Override
//    public void goToShippedOrders() {
//        replaceFragment(ShippedOrdersFragment.newInstance("", ""), "ShippedOrdersFragment", false);
//    }

    @Override
    public void goToViewFullOrder(OrderObject orderObject) {
        replaceFragment(ViewFullOrderFragment.newInstance(orderObject, ""), "ViewFullOrderFragment", true);
    }

/*    @Override
    public void goToRejectedOrders() {
        replaceFragment(RejectedOrdersFragment.newInstance("", ""), "RejectedOrdersFragment", false);
    }

    @Override
    public void goToCanceledOrders() {
        replaceFragment(CanceledOrdersFragment.newInstance("", ""), "CanceledOrdersFragment", false);
    }*/

    @Override
    public void showOrderList(OrdersByType ordersByType) {
        replaceFragment(OrderListFragment.newInstance(ordersByType, ""), "OrderListFragment", true);
    }
}