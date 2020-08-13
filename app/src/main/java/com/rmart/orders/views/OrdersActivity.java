package com.rmart.orders.views;

import android.os.Bundle;

import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.orders.OnOrdersInteractionListener;
import com.rmart.orders.models.OrderListObject;
import com.rmart.orders.models.OrderObject;

public class OrdersActivity extends BaseNavigationDrawerActivity implements OnOrdersInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(OrderHomeFragment.newInstance("", ""), "OrderHomeFragment", true);
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
    public void goToViewFullOrder(OrderListObject orderListObject) {
        replaceFragment(ViewFullOrderFragment.newInstance(orderListObject, ""), "ViewFullOrderFragment", true);
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
    public void showOrderList(OrderObject orderObject) {
        replaceFragment(OrderListFragment.newInstance(orderObject, ""), "OrderListFragment", true);
    }
}