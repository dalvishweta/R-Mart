package com.rmart.customerservice.mobile.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.rmart.customerservice.mobile.models.CCAVenueResponse;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.customerservice.mobile.models.mobileRecharge.Recharge;
import com.rmart.customerservice.mobile.models.mobileRecharge.RechargeBaseClass;
import com.rmart.customerservice.mobile.operators.bottomheet.SelectOperatorBottomSheet;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.customerservice.mobile.repositories.RechargeRepository;
import com.rmart.customerservice.mobile.viewmodels.SelectPlanViewModel;
import com.rmart.customerservice.mobile.views.ServicePaymentActivity;
import com.rmart.databinding.FragmentSelectPlan2Binding;
import com.rmart.electricity.CCavenueres;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.IOnBackPressed;

import static com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge.POSTPAID;
import static com.rmart.customerservice.mobile.fragments.FragmentMobileRecharge.PREPAID;
import static com.rmart.customerservice.mobile.views.ServicePaymentActivity.RESULT;

public class SelectPlanFragment extends Fragment implements IOnBackPressed {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    SelectOperatorBottomSheet bottomSheet;
    SelectCircleBottomSheet selectCircleBottomSheet;
    SelectPlanViewModel mViewModel;
    private String mobile;
    private String name;
    private String type;
    CCavenueres reskdata;
    SlectOperator slectOperator= new SlectOperator(){
        @Override
        public void onSelect(Operator operator) {
            mViewModel.selectedOperatorMutableLiveData.setValue(operator);
            bottomSheet.dismiss();
            if(type.equalsIgnoreCase(POSTPAID)){
                //mViewModel.getPostPaidPlanList();

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
                //mViewModel.getPostPaidPlanList();

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
        mViewModel.type.setValue(type);
        fragmentSelectPlan2Binding.toolbar.setNavigationOnClickListener(view -> {
            if(!onBackPressed() ) {
                getActivity().onBackPressed();
            }
        });
        RechargeBaseClass rechargeBaseClass =  new RechargeBaseClass();
        rechargeBaseClass.setStatus(200);
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
        fragmentSelectPlan2Binding.customAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fragmentSelectPlan2Binding.done.setOnClickListener(view -> {
            if(fragmentSelectPlan2Binding.customAmount.getText().toString().length()>0){

                try {
                    RechargePlans rechargePlans = new RechargePlans();

                    rechargePlans.setRs(Integer.parseInt(fragmentSelectPlan2Binding.customAmount.getText().toString()));
                    mViewModel.rechargePlansMutableLiveData.setValue(rechargePlans);
                } catch (Exception e){
                    mViewModel.rechargePlansMutableLiveData.setValue(null);
                }
            } else {
                mViewModel.rechargePlansMutableLiveData.setValue(null);
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
                  reskdata=rsaKeyResponse.getData();
                  if (rsaKeyResponse.getData().getCcavenue() == 1) {
                      Intent ii = new Intent(getContext(), ServicePaymentActivity.class);
                      ii.putExtra("rsakeyresonse", rsaKeyResponse.getData());

                      ii.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      startActivityForResult(ii, 3333);
                  }else{
                      reskdata.setCcavenue(0);
                      reskdata.setWallet(true);
                      CCavenueres.CcavenueData cc= new CCavenueres.CcavenueData();
                      cc.setAmount(reskdata.getPayment_total_amount());
                      reskdata.setCcavenueData(cc);
                      recharge("null",reskdata);

                      Toast.makeText(getContext(),rsaKeyResponse.getMsg(),Toast.LENGTH_LONG).show();
                  }
              } else {
                  Toast.makeText(getContext(),rsaKeyResponse.getMsg(),Toast.LENGTH_LONG).show();

                  // error Screen
              }
            mViewModel.isLoading.setValue(false);
        });
        return  fragmentSelectPlan2Binding.getRoot();
    }
    private void displayStatus(RechargeBaseClass paymentResponse)

    {

        getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, PaymentStatusFragment.newInstance( paymentResponse, mobile, name,String.valueOf(mViewModel.rechargePlansMutableLiveData.getValue().getRs())))
                    .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            String result = data.getStringExtra(RESULT);
            //Note:-- suggesion dont use directly rokad.in server make call from Rokadmart server using proxy method and keep transaction status update with rokad mart
            if (result != null && requestCode == 3333 && resultCode == ServicePaymentActivity.RESULT_OK) {
                ///JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                recharge(result, reskdata);
            } else {
                recharge(result, reskdata);
            }
        } else {
            Toast.makeText(getContext(),"Payment Gateway transaction Cancel",Toast.LENGTH_LONG).show();
        }

    }
    private void recharge(String data, CCavenueres rsaKeyResponse){
        ProgressDialog progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        progressdialog.show();
        int rechargeType= type.equalsIgnoreCase(POSTPAID)?RechargeRepository.RECHARGE_TYPE_POSTPAID_RECHARGE:RechargeRepository.RECHARGE_TYPE_PREPAID_RECHARGE;
        RechargeRepository.doMobileRecharge(RechargeRepository.SERVICE_TYPE_MOBILE_RECHARGE,null,rechargeType,mViewModel.selectedOperatorMutableLiveData.getValue().type,mViewModel.selectedOperatorMutableLiveData.getValue().type,mobile,RechargeRepository.PLAN_TYPE_SPECIAL_RECHARGE,rsaKeyResponse.getCcavenueData().getAmount()+"",MyProfile.getInstance(getContext()).getUserID(),data,rsaKeyResponse.getCcavenue(),rsaKeyResponse.getRokadOrderId(),rsaKeyResponse.isWallet()).observeForever(new Observer<RechargeBaseClass>() {
            @Override
            public void onChanged(RechargeBaseClass rechargeBaseClass) {
                    // API is not following Restfull gaidlines  so it may cause Error or Exception In future witch may cause in app crashhh
                if(data.equalsIgnoreCase("null")){
                    displayStatus(rechargeBaseClass);
                }
                else{
                    Gson g = new Gson();
                    CCAVenueResponse s = g.fromJson(data, CCAVenueResponse.class);
                    Recharge rs= new Recharge();
                    rs.setTransDate(s.getTransDate());
                    rs.setTrackingId(s.getTrackingId());
                    rechargeBaseClass.setData(rs);
                    displayStatus(rechargeBaseClass);

                }

                    mViewModel.isLoading.setValue(false);
                    progressdialog.dismiss();
            }
        });
    }
    @Override
    public boolean onBackPressed() {
        if(mViewModel.rechargePlansMutableLiveData.getValue()!=null){
            mViewModel.rechargePlansMutableLiveData.setValue(null);
            return true;
        } else {
            return false;
        }
    }
}