package com.rmart.customerservice.mobile.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.adapters.RechargePlansPagerAdapter;
import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPostpaidPlans;
import com.rmart.utilits.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragemtSelectPlan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragemtSelectPlan extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spin,spinloction;
    private Call<ResponseGetPostpaidPlans> getPlansCall;
    private TabLayout plansTabs;
    private ViewPager plansPager;
    private RechargePlansPagerAdapter viewPagerAdapter;
    public FragemtSelectPlan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragemtSelectPlan.
     */
    // TODO: Rename and change types and number of parameters
    public static FragemtSelectPlan newInstance(String param1, String param2) {
        FragemtSelectPlan fragment = new FragemtSelectPlan();
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
        View view = inflater.inflate(R.layout.fragment_select_plan, container, false);

        spin = view.findViewById(R.id.sppiner_opertor);
        spinloction=view.findViewById(R.id.sppiner_location);
        plansTabs = view.findViewById(R.id.recharge_plan_tabs);
        plansTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        plansPager = view.findViewById(R.id.plans_pager);

        final String[] countryCodes = getResources().getStringArray(R.array.mobileopertor);
        ArrayAdapter<String> countryCodeAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.code_spinner_layout,R.id.textView,countryCodes);
        spin.setAdapter(countryCodeAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = String.valueOf(spin.getSelectedItem());
                if (position == 0) {
                    spin.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final String[] locationCodes = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> locationCodesAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.code_spinner_layout,R.id.textView,locationCodes);
        spinloction.setAdapter(locationCodesAdapter);
        spinloction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = String.valueOf(spin.getSelectedItem());
                if (position == 0) {
                    spin.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        MobileRechargeService rechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(MobileRechargeService.class);

                ProgressDialog progressBar = new ProgressDialog(getActivity(), R.style.mySpinnerTheme);
                progressBar.setCancelable(false);
                progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressBar.show();

                // String rechargeType = racType.equals(getString(R.string.prepaid_radio_btn)) ? "P" : "PO";
                    Call<ResponseGetPlans> getPlansCall = rechargeService.getPrepaidPlans("Airtel",
                            "Maharashtra goa","P", BuildConfig.MOBILE_APPLICATION, BuildConfig.MOBILE_VERSION_ID);
                    getPlansCall.enqueue(new Callback<ResponseGetPlans>() {

                        public void onResponse(Call<ResponseGetPlans> call, Response<ResponseGetPlans> response) {
                            Records records = new Records();
                            // progressBar.dismiss();
                            if (response.code() == 200) {
                                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                    records = response.body().getData().getRecords();
                                            /*if (null == records) {
                                                showDialog("", "Plans are not available");
                                            } else {
                                                // mListener.goToSeePlansFragment( response.body().getData().getRecords());
                                            }*/
                                } else {
                                    //showDialog("Sorry!!", "Plans are not available");
                                }
                               getSpecialPlans("8446399429", rechargeService, progressBar, records);
                            } else {
                                showDialog("Sorry!!", "Looks like there's a network or server problem. Please try again in sometime.");
                            }
                        }


                        public void onFailure(Call<ResponseGetPlans> call, Throwable t) {
                            progressBar.dismiss();
                            showDialog("Sorry!!", "Plans are not available");
                        }
                    });



        return view;



    }
    private void getSpecialPlans(String phone, MobileRechargeService rechargeService, ProgressDialog progressBar, Records records) {
        getPlansCall = rechargeService.getPostpaidPlans("Airtel", "Maharashtra goa",
                "PO", phone, BuildConfig.MOBILE_APPLICATION,BuildConfig.MOBILE_VERSION_ID);
        getPlansCall.enqueue(new Callback<ResponseGetPostpaidPlans>() {
            @Override
            public void onResponse(Call<ResponseGetPostpaidPlans> call, Response<ResponseGetPostpaidPlans> response) {
                progressBar.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        List<RechargePlans> data = response.body().getData().getRecords();
                        if (null == data || null == data.get(0).getDesc()|| data.get(0).getDesc().contains("Not Available")) {
                            // showDialog("", "Plans are not available");
                        } else {
                            records.setSpecialPlans(data);
                        }
                    } else {
                        // showDialog("Sorry!!", "Plans are not available");
                    }
                    // showDialog("Sorry!!", "Plans are not available");

                    viewPagerAdapter = new RechargePlansPagerAdapter(getChildFragmentManager(),0, records);
                    plansPager.setAdapter(viewPagerAdapter);
                    plansTabs.setupWithViewPager(plansPager);

                } else {
                    showDialog("Sorry!!", "Looks like there's a network or server problem. Please try again in sometime.");
                }

            }

            @Override
            public void onFailure(Call<ResponseGetPostpaidPlans> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(getContext(), "Please check your Internet connection and try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }







}