package com.rmart.customer.dashboard.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customer.dashboard.adapters.HomeAdapter;
import com.rmart.customer.dashboard.listners.OnClick;
import com.rmart.customer.dashboard.model.BigShopType;
import com.rmart.customer.dashboard.model.ServiceOffer;
import com.rmart.customer.dashboard.model.ShopType;
import com.rmart.customer.dashboard.viewmodel.HomeViewModel;
import com.rmart.customer.shops.list.fragments.VendorShopsListFragment;
import com.rmart.customerservice.dth.actvities.DTHRechargeActivity;
import com.rmart.customerservice.mobile.activities.MobileRechargeActivity;
import com.rmart.databinding.FragmentDashBoardBinding;
import com.rmart.electricity.activities.ElectricityActivity;

public class DashBoardFragment extends BaseFragment {

    public DashBoardFragment() {
        // Required empty public constructor
    }


    public static DashBoardFragment newInstance() {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       FragmentDashBoardBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false);

        HomeViewModel mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setHomeViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.loadShopHomePage();
        mViewModel.shopHomePageResponceMutableLiveData.observeForever(homePageResponse -> {
            HomeAdapter homeAdapter = new HomeAdapter(getActivity(), new OnClick() {

                @Override
                public void onServiceClick(ServiceOffer serviceOffer) {
                    if(serviceOffer.getServiceCaption().equalsIgnoreCase("mobile-recharge")){
                        Intent intent = new Intent(getContext(), MobileRechargeActivity.class);
                        startActivity(intent);
                    }
                    if(serviceOffer.getServiceCaption().equalsIgnoreCase("dth-recharge")){
                       Intent intent = new Intent(getContext(), DTHRechargeActivity.class);
                       startActivity(intent);
                        // Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                    }
                    if(serviceOffer.getServiceCaption().equalsIgnoreCase("light-bill")){
                        Intent intent = new Intent(getContext(), ElectricityActivity.class);
                        startActivity(intent);
                    }
                    if(serviceOffer.getServiceCaption().equalsIgnoreCase("travel-booking")){
//                        Intent intent = new Intent(getContext(), ActivityElectricity.class);
//                        startActivity(intent);
                        Toast.makeText(getContext(),"Comming Soon",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onShopClick(ShopType serviceOffer) {
                    openShopList(serviceOffer.getClick());

                }

                @Override
                public void onBigShopClick(BigShopType bigShopType) {

                    if(bigShopType.getName().equalsIgnoreCase("Hand crafted shop")) {
                        Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                    } else {
                        openShopList(null);
                    }



                }
            },homePageResponse.getData());
            binding.setMyAdapter(homeAdapter);
        });

       return binding.getRoot();
    }

    private void openShopList(String serviceOffer) {
        VendorShopsListFragment vendorShopsListFragment =  VendorShopsListFragment.getInstance(serviceOffer);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.base_container, vendorShopsListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}