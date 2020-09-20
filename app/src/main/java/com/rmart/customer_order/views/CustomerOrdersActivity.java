package com.rmart.customer_order.views;

import android.os.Bundle;
import android.view.View;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer_order.OnCustomerOrdersInteractionListener;
import com.rmart.utilits.pojos.orders.Order;


public class CustomerOrdersActivity extends BaseNavigationDrawerActivity implements OnCustomerOrdersInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(CustomerOrderListFragment.newInstance( ""), "OrderHomeFragment", false);
    }


    @Override
    public void goToViewFullOrder(Order orderObject) {
        hideHamburgerIcon();
        replaceFragment(CustomerViewFullOrderFragment.newInstance(orderObject, ""), "ViewFullOrderFragment", true);
    }


    @Override
    public void showOrderList(String string) {
        hideHamburgerIcon();
        replaceFragment(CustomerOrderListFragment.newInstance(string), "OrderListFragment", true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.customer_orders) {
            getToActivity(view.getId(), false);
        } else {
            getToActivity(view.getId(), true);
        }
    }

    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }
}