package com.rmart.orders;

import com.rmart.baseclass.BaseListener;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.models.OrdersByType;

public interface OnOrdersInteractionListener extends BaseListener {
    void goToHome();
    /*void goToOpenOrders();
    void goToAcceptedOrders();
    void goToPackedOrders();
    void goToShippedOrders();*/
    void goToViewFullOrder(OrderObject orderObject);
//    void goToRejectedOrders();
//    void goToCanceledOrders();
    void showOrderList(OrdersByType ordersByType);
}
