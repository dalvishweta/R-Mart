package com.rmart.customerservice.mobile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.rmart.R;
import com.rmart.customerservice.mobile.adapters.RechargePlansAdapter;
import com.rmart.customerservice.mobile.adapters.RechargePlansPagerAdapter;
import com.rmart.customerservice.mobile.circle.bottomsheets.SelectCircleBottomSheet;
import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.listners.SlectCircle;
import com.rmart.customerservice.mobile.listners.SlectOperator;
import com.rmart.customerservice.mobile.models.MRechargeBaseClass;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.customerservice.mobile.operators.bottomheet.SelectOperatorBottomSheet;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.customerservice.mobile.repositories.MobileRechargeRepository;
import com.rmart.customerservice.mobile.viewmodels.SelectPlanViewModel;
import com.rmart.customerservice.mobile.views.ServicePaymentActivity;
import com.rmart.databinding.FragmentSelectPlan2Binding;
import com.rmart.electricity.CCAvenueResponceModel;
import com.rmart.profile.model.MyProfile;

import static com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge.POSTPAID;
import static com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge.PREPAID;
import static com.rmart.customerservice.mobile.repositories.MobileRechargeRepository.MOBLIE_RECHARGE_SERVICE_TYPE;
import static com.rmart.customerservice.mobile.views.ServicePaymentActivity.RESULT;

public class SelectPlanFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    SelectOperatorBottomSheet bottomSheet;
    SelectCircleBottomSheet selectCircleBottomSheet;
    SelectPlanViewModel mViewModel;
    private String mobile;
    private String name;
    private String type;
    SlectOperator slectOperator= new SlectOperator(){
        @Override
        public void onSelect(Operator operator) {
            mViewModel.selectedOperatorMutableLiveData.setValue(operator);
            bottomSheet.dismiss();
            if(type.equalsIgnoreCase(POSTPAID)){
                mViewModel.getPostPaidPlanList();

            } else {
                mViewModel.getPrePaidPlanList();
            }
        }
    };
    SlectCircle slectCircle= new SlectCircle(){
        @Override
        public void onSelect(Circle circle) {
            mViewModel.circleMutableLiveData.setValue(circle);
            selectCircleBottomSheet.dismiss();
            if(type.equalsIgnoreCase(POSTPAID)){
                mViewModel.getPostPaidPlanList();

            } else {
                mViewModel.getPrePaidPlanList();
            }
        }
    };
    public SelectPlanFragment() {
        // Required empty public constructor
    }
    public static SelectPlanFragment newInstance(String mobile, String name,String type) {
        SelectPlanFragment fragment = new SelectPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mobile);
        args.putString(ARG_PARAM3, type);
        args.putString(ARG_PARAM2, name);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mobile = getArguments().getString(ARG_PARAM1);
            name = getArguments().getString(ARG_PARAM2);
            type = getArguments().getString(ARG_PARAM3);
            if(type==null){
                type= PREPAID;
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentSelectPlan2Binding fragmentSelectPlan2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_plan2, container, false);
        mViewModel = new ViewModelProvider(this).get(SelectPlanViewModel.class);
        mViewModel.mobile.setValue(mobile);
        mViewModel.name.setValue(name);
        fragmentSelectPlan2Binding.toolbar.setNavigationOnClickListener(view -> {
            getActivity().onBackPressed();
        });
        fragmentSelectPlan2Binding.setSelectPlanViewModel(mViewModel);
        fragmentSelectPlan2Binding.setLifecycleOwner(this);
        fragmentSelectPlan2Binding.operatorSelect.setOnClickListener(view -> {
            if(mViewModel.rechargePlansMutableLiveData.getValue()==null) {
                bottomSheet = new SelectOperatorBottomSheet(slectOperator, type);
                bottomSheet.show(getActivity().getSupportFragmentManager(),
                        "ModalBottomSheet");
            }
        });
        fragmentSelectPlan2Binding.selctCircle.setOnClickListener(view -> {
            if(mViewModel.rechargePlansMutableLiveData.getValue()==null) {
                selectCircleBottomSheet = new SelectCircleBottomSheet(slectCircle);
                selectCircleBottomSheet.show(getActivity().getSupportFragmentManager(),
                        "ModalBottomSheet");
            }
        });
        if(type.equalsIgnoreCase(POSTPAID)) {
            fragmentSelectPlan2Binding.plansPager.setVisibility(View.GONE);
            fragmentSelectPlan2Binding.postPaidPlans.setVisibility(View.VISIBLE);
        } else {
            fragmentSelectPlan2Binding.plansPager.setVisibility(View.VISIBLE);
            fragmentSelectPlan2Binding.postPaidPlans.setVisibility(View.GONE);
        }
        mViewModel.mplanListResponseMutableLiveData.observeForever(mplanBaseResponse -> {
            if (mplanBaseResponse!=null && mplanBaseResponse.getData() != null){
                Records records = mplanBaseResponse.getData().getRecords();
                RechargePlansPagerAdapter viewPagerAdapter = new RechargePlansPagerAdapter(getChildFragmentManager(), 0, records, chosenSubscriber -> {
                    mViewModel.rechargePlansMutableLiveData.setValue(chosenSubscriber);
                });
            fragmentSelectPlan2Binding.plansPager.setAdapter(viewPagerAdapter);
            fragmentSelectPlan2Binding.rechargePlanTabs.setupWithViewPager(fragmentSelectPlan2Binding.plansPager);
        }
        });

        mViewModel.postPaidResponseGetPlansMutableLiveData.observeForever(postPaidResponseGetPlans -> {


            if(postPaidResponseGetPlans!=null && postPaidResponseGetPlans.getData()!=null) {

                    RechargePlansAdapter adapter = new RechargePlansAdapter(chosenSubscriber -> mViewModel.rechargePlansMutableLiveData.setValue(chosenSubscriber), getContext(), postPaidResponseGetPlans.getData().getRecords());
                    fragmentSelectPlan2Binding.postPaidPlans.setAdapter(adapter);

            }
        });
        fragmentSelectPlan2Binding.change.setOnClickListener(view -> mViewModel.rechargePlansMutableLiveData.setValue(null));
        fragmentSelectPlan2Binding.recharge.setOnClickListener(view -> mViewModel.getRsaKey(MyProfile.getInstance(getContext()).getUserID()));
        mViewModel.responseRsakeyMutableLiveData.observeForever(rsaKeyResponse -> {

              if(rsaKeyResponse!=null && rsaKeyResponse.getStatus().equals("success")) {

                  Intent ii= new Intent(getContext(), ServicePaymentActivity.class);
                  ii.putExtra("rsakeyresonse",  rsaKeyResponse.getData());
                  startActivityForResult(ii,3333);
                  mViewModel.isLoading.setValue(false);
              } else {

                  // error Screen
              }
        });
        return  fragmentSelectPlan2Binding.getRoot();
    }
    private void displayStatus(int statusType,CCAvenueResponceModel ccavanueResponse)
    {
        getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, PaymentStatusFragment.newInstance(statusType, ccavanueResponse, mobile, name)).addToBackStack(null)
                    .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = data.getStringExtra(RESULT);

        try {

            //Note:-- suggesion dont use directly rokad.in server make call from Rokadmart server using proxy method and keep transaction status update with rokad mart

            if (result!=null && requestCode == 3333 && resultCode == ServicePaymentActivity.RESULT_OK) {
                ///JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                Gson g = new Gson();
                CCAvenueResponceModel ccAvenueResponse = g.fromJson(result, CCAvenueResponceModel.class);



                if (ccAvenueResponse.getOrderStatus().equalsIgnoreCase("success")) {
                    // inform to server transaction success and its server responsibility to perform recharge operation and paymanet validation and response witch transaction status
                    // depending upon response display status message
               MobileRechargeRepository.performVRecharge(MOBLIE_RECHARGE_SERVICE_TYPE, null, null, type.equalsIgnoreCase(PREPAID) ? 1 : 2, type.equalsIgnoreCase(PREPAID) ? mViewModel.selectedOperatorMutableLiveData.getValue().type : null, type.equalsIgnoreCase(POSTPAID) ? mViewModel.selectedOperatorMutableLiveData.getValue().type : null, mViewModel.circleMutableLiveData.getValue().name, mobile, type.equalsIgnoreCase(PREPAID) ? 1 : 2, mViewModel.rechargePlansMutableLiveData.getValue().getRs() + "", MyProfile.getInstance(getContext()).getUserID(), result).observeForever(new Observer<MRechargeBaseClass>() {
                   @Override
                   public void onChanged(MRechargeBaseClass mRechargeBaseClass) {

                   }
               });

                } else {


                    /// inform to server transaction failed with failed data
                    displayStatus(PaymentStatusFragment.ERROR,ccAvenueResponse);

                }





            }
            else {

                //reporting to server regarding Transaction cancel by back press or other way and dispay appropriate message from Server so we get Dynamic error message
            }
        } catch (Exception e){

            //reporting to server regarding Json Error or other error
//

        }

    }
}