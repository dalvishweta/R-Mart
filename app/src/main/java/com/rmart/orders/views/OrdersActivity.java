package com.rmart.orders.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.orders.OnOrdersInteractionListener;

public class OrdersActivity extends BaseNavigationDrawerActivity implements OnOrdersInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(OrderHomeFragment.newInstance("", ""), "OrderHomeFragment", false);
    }

    @Override
    public void goToHome() {
       replaceFragment(OrderHomeFragment.newInstance("", ""), "OrderHomeFragment", false);
    }

    @Override
    public void goToOpenOrders() {
        replaceFragment(OpenOrdersFragment.newInstance("", ""), "OpenOrdersFragment", false);
    }

    @Override
    public void goToAcceptedOrders() {
        replaceFragment(AcceptedOrdersFragment.newInstance("", ""), "AcceptedOrdersFragment", false);
    }

    @Override
    public void goToPackedOrders() {
        replaceFragment(PackedOrdersFragment.newInstance("", ""), "PackedOrdersFragment", false);
    }

    @Override
    public void goToShippedOrders() {
        replaceFragment(ShippedOrdersFragment.newInstance("", ""), "ShippedOrdersFragment", false);
    }

    @Override
    public void goToDeliveredOrders() {
        replaceFragment(DeliveredOrdersFragment.newInstance("", ""), "DeliveredOrdersFragment", false);
    }

    @Override
    public void goToRejectedOrders() {
        replaceFragment(RejectedOrdersFragment.newInstance("", ""), "RejectedOrdersFragment", false);
    }

    @Override
    public void goToCanceledOrders() {
        replaceFragment(CanceledOrdersFragment.newInstance("", ""), "CanceledOrdersFragment", false);
    }
}