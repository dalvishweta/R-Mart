package com.rmart.customerservice.mobile.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.customerservice.mobile.views.MobileRechargePlans;

import java.util.ArrayList;

public class RechargePlansPagerAdapter extends FragmentStatePagerAdapter {


    private ArrayList<BaseFragment> rechargePlansFrags;
    private ArrayList<String> fragTitles;

    public RechargePlansPagerAdapter(@NonNull FragmentManager fm, int behavior, Records records) {
        super(fm,behavior);

        rechargePlansFrags = new ArrayList<>();
        fragTitles = new ArrayList<>();

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
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getTOPUP()));
        }
        if(records.getRomaing().size() > 0 ) {
            fragTitles.add("Roaming");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getRomaing()));
        }
        if(records.getCOMBO().size() > 0 ) {
            fragTitles.add("COMBO");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getCOMBO()));
        }
        if(records.getRATECUTTER().size() > 0 ) {
            fragTitles.add("Rate Cutters");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getRATECUTTER()));
        }
        if(records.getSMS().size() > 0 ) {
            fragTitles.add("SMS");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getSMS()));
        }

        if(records.getG2().size() > 0 ) {
            fragTitles.add("2G");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getG2()));
        }
        if(records.getG4G3().size() > 0 ) {
            fragTitles.add("3G/4G");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getG4G3()));
        }

        if(records.getSpecialPlan().size() > 0 ) {
            fragTitles.add("Special Plans");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getSpecialPlan()));
        }

        if(records.getFullTT().size() > 0 ) {
            fragTitles.add("Full Talk Time");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getFullTT()));
        }

        if(records.getFRC().size() > 0 ) {
            fragTitles.add("FRC");
            rechargePlansFrags.add(MobileRechargePlans.newInstance(records.getFRC()));
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
