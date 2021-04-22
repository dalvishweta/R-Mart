package com.rmart.customerservice.mobile.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.interfaces.OnPlanSelectedHandler;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.customerservice.mobile.fragments.MobileRechargePlans;

import java.util.ArrayList;

public class RechargePlansPagerAdapter extends FragmentStatePagerAdapter {

    private OnPlanSelectedHandler onPlanSelectedHandler;
    private ArrayList<BaseFragment> rechargePlansFrags;
    private ArrayList<String> fragTitles;

    public RechargePlansPagerAdapter(@NonNull FragmentManager fm, int behavior, Records records, OnPlanSelectedHandler onPlanSelectedHandler) {
        super(fm,behavior);

        rechargePlansFrags = new ArrayList<>();
        fragTitles = new ArrayList<>();
        this.onPlanSelectedHandler = onPlanSelectedHandler;

//        rechargePlansFrags.add(RechargeTopUpPlans.newInstance(topup));
//        rechargePlansFrags.add(RoamingPlans.newInstance(roaming));
//        rechargePlansFrags.add(ComboPlans.newInstance(combo));
//        rechargePlansFrags.add(RateCutterPlans.newInstance(ratecutter));
//        rechargePlansFrags.add(SMSPlans.newInstance(sm));
//
//        fragTitles.add("TopUp");
//        fragTitles.add("Roaming");
//        fragTitles.add("Combo Plans");
//        fragTitles.add("Rate Cutters");
//        fragTitles.add("SMS plans");

        if(records.getTOPUP().size() > 0 ) {
            fragTitles.add("TopUp");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getTOPUP());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
        }
        if(records.getRomaing().size() > 0 ) {
            fragTitles.add("Roaming");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getRomaing());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
        }
        if(records.getCOMBO().size() > 0 ) {
            fragTitles.add("Combo");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getCOMBO());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
       }
        if(records.getRATECUTTER().size() > 0 ) {
            fragTitles.add("Rate Cutters");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getRATECUTTER());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
        }
        if(records.getSMS().size() > 0 ) {
            fragTitles.add("SMS");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getSMS());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
        }

        if(records.getG2().size() > 0 ) {
            fragTitles.add("2G");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getG2());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);

        }
        if(records.getG4G3().size() > 0 ) {
            fragTitles.add("3G/4G");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getG4G3());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);

        }

        if(records.getSpecialPlan().size() > 0 ) {
            fragTitles.add("Special Plans");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getSpecialPlan());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
        }

        if(records.getFullTT().size() > 0 ) {
            fragTitles.add("Full Talk Time");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getFullTT());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
        }

        if(records.getFRC().size() > 0 ) {
            fragTitles.add("FRC");
            MobileRechargePlans mobileRechargePlans = MobileRechargePlans.newInstance(records.getFRC());
            mobileRechargePlans.setOnPlanSelectedHandler(onPlanSelectedHandler);
            rechargePlansFrags.add(mobileRechargePlans);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return rechargePlansFrags.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragTitles.get(position);
    }

    @Override
    public int getCount() {
        return rechargePlansFrags.size();
    }


}
