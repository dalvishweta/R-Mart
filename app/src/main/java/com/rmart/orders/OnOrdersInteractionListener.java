package com.rmart.orders;

import com.rmart.baseclass.BaseListener;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.models.SelectedOrderGroup;

public interface OnOrdersInteractionListener extends BaseListener {
    void goToHome();
    void goToViewFullOrder(OrderObject orderObject);
    void goToOTPValidation(OrderObject orderObject);
    void goToProcessToDelivery(OrderObject orderObject);
    void showOrderList();
}
