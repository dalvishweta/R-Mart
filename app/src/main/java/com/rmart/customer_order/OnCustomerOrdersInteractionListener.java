package com.rmart.customer_order;

import com.rmart.baseclass.BaseListener;
import com.rmart.utilits.pojos.orders.Order;

public interface OnCustomerOrdersInteractionListener extends BaseListener {
    void goToViewFullOrder(Order orderObject);
    void showOrderList(String stateOfOrders);
}
