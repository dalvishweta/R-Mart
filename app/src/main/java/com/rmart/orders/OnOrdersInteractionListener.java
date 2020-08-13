package com.rmart.orders;

import com.rmart.baseclass.BaseListener;
import com.rmart.orders.models.OrderListObject;
import com.rmart.orders.models.OrderObject;

public interface OnOrdersInteractionListener extends BaseListener {
    void goToHome();
    void goToOpenOrders();
    void goToAcceptedOrders();
    void goToPackedOrders();
    void goToShippedOrders();
    void goToViewFullOrder(OrderListObject orderListObject);
    void goToRejectedOrders();
    void goToCanceledOrders();
    void showOrderList(OrderObject orderObject);
}
