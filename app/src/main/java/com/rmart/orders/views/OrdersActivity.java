package com.rmart.orders.views;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.rmart.R;
import com.rmart.baseclass.views.BaseNavigationDrawerActivity;
import com.rmart.orders.OnOrdersInteractionListener;
import com.rmart.orders.models.OrderObject;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.orders.Order;
import com.rmart.utilits.pojos.orders.StateOfOrders;

import java.util.Objects;

import static com.rmart.fcm.MyFirebaseMessagingService.ORDER_ID;


public class OrdersActivity extends BaseNavigationDrawerActivity implements OnOrdersInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (MyProfile.getInstance(getApplicationContext()).getRoleID().equals(Utils.DELIVERY_ID)) {
                StateOfOrders stateOfOrders =  new StateOfOrders();
                stateOfOrders.setStatus(Utils.SHIPPED_ORDER_STATUS);
                stateOfOrders.setStatusName("Shipped Orders");
                addFragment(OrderListFragment.newInstance(stateOfOrders), "OrderListFragment", false);

                if (toolbar != null) {
                    //setTitle(getResources().getString(R.string.app_name));
                    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                    toolbar.setVisibility(View.INVISIBLE);
                    actionBarDrawerToggle.syncState();
                }
            } else if (MyProfile.getInstance(getApplicationContext()).getRoleID().equals(Utils.RETAILER_ID)) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    hideHamburgerIcon();
                    String orderID = extras.getString(ORDER_ID);
                    Order lOrderDetails = new Order();
                    lOrderDetails.setOrderID(orderID);
                    replaceFragment(ViewFullOrderFragment.newInstance(lOrderDetails, ""), "ViewFullOrderFragment", false);
                } else {
                    addFragment(OrderHomeFragment.newInstance("", ""), "OrderHomeFragment", false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    @Override
    public void goToHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void goToViewFullOrder(Order orderObject) {
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
    public void showOrderList(StateOfOrders stateOfOrders) {
        hideHamburgerIcon();
        replaceFragment(OrderListFragment.newInstance(stateOfOrders), "OrderListFragment", true);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.retailer_orders) {
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