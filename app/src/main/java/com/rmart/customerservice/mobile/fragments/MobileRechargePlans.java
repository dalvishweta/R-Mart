package com.rmart.customerservice.mobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.adapters.RechargePlansAdapter;
import com.rmart.customerservice.mobile.interfaces.OnMobileRechargeListener;
import com.rmart.customerservice.mobile.interfaces.OnPlanSelectedHandler;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;

import java.io.Serializable;
import java.util.List;

public class MobileRechargePlans extends BaseFragment  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public void setOnPlanSelectedHandler(OnPlanSelectedHandler onPlanSelectedHandler) {
        this.onPlanSelectedHandler = onPlanSelectedHandler;
    }

    OnPlanSelectedHandler onPlanSelectedHandler;
    private String mParam1;
    private String mParam2;
    private List<RechargePlans> sm;
    public MobileRechargePlans() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMobileRechargeListener) {
        }
    }



    public static MobileRechargePlans newInstance(List<RechargePlans> sm) {
        MobileRechargePlans fragment = new MobileRechargePlans();
        Bundle args = new Bundle();
        args.putSerializable("sm", (Serializable) sm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          sm = (List<RechargePlans>) getArguments().getSerializable("sm");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rechagrge_plans, container, false);

        RecyclerView smsPlansList = view.findViewById(R.id.sms_plans);

        if (sm.isEmpty()){
            smsPlansList.setVisibility(View.GONE);
            view.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        } else {
            RechargePlansAdapter adapter = new RechargePlansAdapter(onPlanSelectedHandler, getContext(), sm);
            smsPlansList.setAdapter(adapter);
        }

        return view;
    }


}
