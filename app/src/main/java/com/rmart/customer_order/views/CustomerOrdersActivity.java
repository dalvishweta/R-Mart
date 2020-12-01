package com.rmart.customer_order.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.customer_order.OnCustomerOrdersInteractionListener;
import com.rmart.utilits.pojos.customer_orders.CustomerOrderProductList;
import com.rmart.utilits.pojos.orders.Order;

import static com.rmart.fcm.MyFirebaseMessagingService.ORDER_ID;


public class CustomerOrdersActivity extends BaseNavigationDrawerActivity implements OnCustomerOrdersInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String orderID = extras.getString(ORDER_ID);
            if(!TextUtils.isEmpty(orderID)) {
                Order lOrderDetails = new Order();
                lOrderDetails.setOrderID(orderID);
                replaceFragment(CustomerViewFullOrderFragment.newInstance(lOrderDetails, ""), CustomerViewFullOrderFragment.class.getName(), false);
            } else {
                replaceFragment(CustomerOrderListFragment.newInstance(), CustomerOrderListFragment.class.getName(), false);
            }
        } else {
            replaceFragment(CustomerOrderListFragment.newInstance(), CustomerOrderListFragment.class.getName(), false);
        }
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    @Override
    public void goToViewFullOrder(Order orderObject) {
        hideHamburgerIcon();
        replaceFragment(CustomerViewFullOrderFragment.newInstance(orderObject, ""), CustomerViewFullOrderFragment.class.getName(), true);
    }


    @Override
    public void showOrderList(String string) {
        hideHamburgerIcon();
        replaceFragment(CustomerOrderListFragment.newInstance(), CustomerOrderListFragment.class.getName(), true);
    }

    @Override
    public void goToReOrder(CustomerOrderProductList order) {
        replaceFragment(CheckAvailableProducts.newInstance(order,""), CheckAvailableProducts.class.getName(), true);
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