package com.rmart.customerservice.mobile.views;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.adapters.MobileRechargePagerAdapter;


public class MobileHomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MobileRechargePagerAdapter viewPagerAdapter;

    private String mParam1;
    private String mParam2;

    public MobileHomeFragment() {
        // Required empty public constructor
    }

    public static MobileHomeFragment newInstance(String param1, String param2) {
        MobileHomeFragment fragment = new MobileHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobile_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager =  view.findViewById(R.id.mobile_recharge_pager);
        viewPagerAdapter = new MobileRechargePagerAdapter(getChildFragmentManager(),0);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = view.findViewById(R.id.mobile_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicator(R.drawable.rounded_tab_selected_indicator);
        tabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.tab_normal_color)));

        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        int initialPos = 1;
//        if (initialPos == position){
//            Fragment fragment = viewPagerAdapter.getItem(0);
//
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
