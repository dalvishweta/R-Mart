package com.rmart.customer.dashboard.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.services.AuthenticationService;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        checkRegistration();

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
                        //Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                        //5857131402
                    }
                    if(serviceOffer.getServiceCaption().equalsIgnoreCase("light-bill")){
                        Intent intent = new Intent(getContext(), ElectricityActivity.class);
                        startActivity(intent);
                    }
                    if(serviceOffer.getServiceCaption().equalsIgnoreCase("travel-booking")){
//                        Intent intent = new Intent(getContext(), ActivityElectricity.class);
//                        startActivity(intent);
                        Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onShopClick(ShopType serviceOffer) {
                    openShopList(serviceOffer.getClick());

                }

                @Override
                public void onBigShopClick(BigShopType bigShopType) {
                        openShopList(bigShopType.getClick()+"");
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
    private void checkRegistration() {
        if (Utils.isNetworkConnected(getContext())) {

            ProgressDialog progressBar = new ProgressDialog(getActivity(), R.style.mySpinnerTheme);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressBar.show();

            AuthenticationService getUserDataService = RetrofitClientInstance.getInstance().getRetrofitInstanceRokad().create(AuthenticationService.class);
            Call<BaseResponse> user = getUserDataService.registrationRokad(MyProfile.getInstance(getContext()).getFirstName(),(MyProfile.getInstance(getContext()).getLastName()),(MyProfile.getInstance(getContext()).getMobileNumber()),(MyProfile.getInstance(getContext()).getEmail()), "rokad","rokad",(MyProfile.getInstance(getContext()).getUserID()));//("", "");
            user.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Log.d("onResponse", "onResponse: Login Fragment");
                    progressBar.dismiss();
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                      //  showDialog("success", response.body().getMsg());

                    } else if (response.body() != null){
                       /// showDialog("Sorry..", response.body().getMsg());
                    } else {

                    }
                    progressBar.dismiss();
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    // Log.d("onFailure", "onFailure: Login Fragment ");
                    progressBar.dismiss();
                    if(t instanceof SocketTimeoutException){
                        showDialog(getString(R.string.time_out_title), getString(R.string.time_out_msg));
                    } else {
                        showDialog("Sorry..!!", getString(R.string.server_failed_case));
//                            Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    // showDialog("Sorry..", getString(R.string.internet_failed_login_case));
                }
            });


        } else {
            showDialog("Sorry!!", getString(R.string.internet_check));
        }
    }
}