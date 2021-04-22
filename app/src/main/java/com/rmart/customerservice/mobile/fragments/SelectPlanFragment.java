package com.rmart.customerservice.mobile.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rmart.R;
import com.rmart.customerservice.mobile.adapters.RechargePlansAdapter;
import com.rmart.customerservice.mobile.interfaces.OnPlanSelectedHandler;
import com.rmart.customerservice.mobile.models.mPlans.PostPaidResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.customerservice.mobile.adapters.RechargePlansPagerAdapter;
import com.rmart.customerservice.mobile.circle.bottomsheets.SelectCircleBottomSheet;
import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.listners.SlectCircle;
import com.rmart.customerservice.mobile.listners.SlectOperator;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;
import com.rmart.customerservice.mobile.operators.bottomheet.SelectOperatorBottomSheet;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.customerservice.mobile.viewmodels.SelectPlanViewModel;
import com.rmart.databinding.FragmentSelectPlan2Binding;

import java.util.List;

public class SelectPlanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    SelectOperatorBottomSheet bottomSheet;
    SelectCircleBottomSheet selectCircleBottomSheet;
    SelectPlanViewModel mViewModel;

    // TODO: Rename and change types of parameters
    private String mobile;
    private String name;
    private String type;
    SlectOperator slectOperator= new SlectOperator(){

        @Override
        public void onSelect(Operator operator) {
            mViewModel.selectedOperatorMutableLiveData.setValue(operator);
            bottomSheet.dismiss();
            if(type.equalsIgnoreCase(FragmentMobileRecharge.POSTPAID)){
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
            if(type.equalsIgnoreCase(FragmentMobileRecharge.POSTPAID)){
                mViewModel.getPostPaidPlanList();

            } else {
                mViewModel.getPrePaidPlanList();
            }
        }


    };

    public SelectPlanFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
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
                type=FragmentMobileRecharge.PREPAID;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentSelectPlan2Binding fragmentSelectPlan2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_plan2, container, false);
        mViewModel = new ViewModelProvider(this).get(SelectPlanViewModel.class);
        mViewModel.mobile.setValue(mobile);
        mViewModel.name.setValue(name);
        fragmentSelectPlan2Binding.setSelectPlanViewModel(mViewModel);
        fragmentSelectPlan2Binding.setLifecycleOwner(this);
        fragmentSelectPlan2Binding.operatorSelect.setOnClickListener(view -> {
            bottomSheet = new SelectOperatorBottomSheet(slectOperator,type);
            bottomSheet.show(getActivity().getSupportFragmentManager(),
                    "ModalBottomSheet");
        });
        fragmentSelectPlan2Binding.selctCircle.setOnClickListener(view -> {
            selectCircleBottomSheet = new SelectCircleBottomSheet(slectCircle);
            selectCircleBottomSheet.show(getActivity().getSupportFragmentManager(),
                    "ModalBottomSheet");
        });
        if(type.equalsIgnoreCase(FragmentMobileRecharge.POSTPAID)) {
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
                    fragmentSelectPlan2Binding.setRechargePlans(chosenSubscriber);
                });
            fragmentSelectPlan2Binding.plansPager.setAdapter(viewPagerAdapter);
            fragmentSelectPlan2Binding.rechargePlanTabs.setupWithViewPager(fragmentSelectPlan2Binding.plansPager);
        }
        });

        mViewModel.postPaidResponseGetPlansMutableLiveData.observeForever(postPaidResponseGetPlans -> {


            if(postPaidResponseGetPlans!=null && postPaidResponseGetPlans.getData()!=null) {

                    RechargePlansAdapter adapter = new RechargePlansAdapter(chosenSubscriber -> fragmentSelectPlan2Binding.setRechargePlans(chosenSubscriber), getContext(), postPaidResponseGetPlans.getData().getRecords());
                    fragmentSelectPlan2Binding.postPaidPlans.setAdapter(adapter);

            }
        });

        return  fragmentSelectPlan2Binding.getRoot();
    }


}