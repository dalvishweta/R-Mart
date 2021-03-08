package com.rmart.customerservice.mobile.interfaces;


import com.rmart.customerservice.mobile.models.MobileRecharge;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.Records;

import java.util.List;

public interface OnMobileRechargeListener {
    MobileRecharge getMobileRechargeModule();
    void resetMobileRechargeModule();
    void goToMakePaymentFragment();
    void goToSeePlansFragment(List<RechargePlans> topup);
    void makeAnotherPayment();
    void updatePrice();

    void goToSeePlansFragment(Records data);
}
