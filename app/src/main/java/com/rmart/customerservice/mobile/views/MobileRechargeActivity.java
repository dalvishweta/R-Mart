package com.rmart.customerservice.mobile.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.rmart.R;
import com.rmart.customerservice.mobile.interfaces.OnMobileRechargeListener;
import com.rmart.customerservice.mobile.models.MobileRecharge;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.profile.model.MyProfile;

import java.util.List;

public class MobileRechargeActivity extends ServicesBaseActivity implements OnMobileRechargeListener {

    MobileRecharge mobileRecharge = new MobileRecharge(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_view);
        addFragment(RechargeHomeFragment.newInstance("",""),"FragemtSelectPlan",false);
    }

    @Override
    public MobileRecharge getMobileRechargeModule() {
        return mobileRecharge;
    }

    @Override
    public void resetMobileRechargeModule() {
        mobileRecharge = new MobileRecharge(this);
    }

    @Override
    public void goToMakePaymentFragment() {
        replaceFragment(MakePaymentFragment.newInstance("",""), "MakePaymentFragment", true);
    }

    @Override
    public void goToSeePlansFragment(List<RechargePlans> topup) {
        Records data = new Records();
        data.setSpecialPlans(topup);
        replaceFragment(MobileRechargePlansHome.newInstance(data), "MobileRechargePlansHome", true);
    }

    @Override
    public void makeAnotherPayment() {
        Intent intent = new Intent(getApplicationContext(), MobileRechargeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        //replaceFragment(MobileHomeFragment.newInstance("",""),"MobileHomeFragment",false);
    }

    @Override
    public void updatePrice() {
        Fragment navHostFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
        assert navHostFragment != null;

    }


    @Override
    public void goToSeePlansFragment(Records data) {
        replaceFragment(MobileRechargePlansHome.newInstance(data), "MobileRechargePlansHome", true);
    }
}
