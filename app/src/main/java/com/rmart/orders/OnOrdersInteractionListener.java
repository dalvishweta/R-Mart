package com.rmart.orders;

import com.rmart.baseclass.BaseListener;

public interface OnOrdersInteractionListener extends BaseListener {
    void goToHome();
    void goToOpenOrders();
    void goToAcceptedOrders();
    void goToPackedOrders();
    void goToShippedOrders();
    void goToDeliveredOrders();
    void goToRejectedOrders();
    void goToCanceledOrders();
}
