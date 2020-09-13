package com.rmart.orders;

import com.rmart.baseclass.BaseListener;
import com.rmart.orders.models.OrderObject;
import com.rmart.utilits.pojos.orders.Order;
import com.rmart.utilits.pojos.orders.StateOfOrders;

public interface OnOrdersInteractionListener extends BaseListener {
    void goToHome();
    void goToViewFullOrder(Order orderObject);
    void goToOTPValidation(OrderObject orderObject);
    void goToProcessToDelivery(OrderObject orderObject);
    void showOrderList(StateOfOrders stateOfOrders);
}
