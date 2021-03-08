package com.rmart.customerservice.mobile.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.rmart.BuildConfig;
import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.adapters.SubscriberListAdapter;
import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.interfaces.OnMobileRechargeListener;
import com.rmart.customerservice.mobile.interfaces.RecyclerOnClickHandler;
import com.rmart.customerservice.mobile.models.SubscriberModule;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.customerservice.mobile.models.mPlans.Records;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPlans;
import com.rmart.customerservice.mobile.models.mPlans.ResponseGetPostpaidPlans;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rmart.utilits.Utils.isValidMobile;


public class RechargeHomeFragment extends BaseFragment implements View.OnClickListener,
        RecyclerOnClickHandler, RadioGroup.OnCheckedChangeListener{

    private static RechargeHomeFragment fragment;
    private ArrayList<SubscriberModule> subscriberModules = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText mobileRechargeNum, rechargeAmount,vc_num;
    private String mParam1;
    private String mParam2;
    private OnMobileRechargeListener mListener;
    private ArrayAdapter<String> statesSpinnerAdapter;
    private TextInputLayout mobile_hint, vc_hint;
    private RadioGroup rechargeTypeGroup;
     RadioButton rechargeType;
    private String racType;
    private boolean perfectMobileNumer;
    private RecyclerView recyclerView;
    private AppCompatTextView noServiceTxtView;
    RelativeLayout mobile_relative,dth_relative;
    private LinearLayout amountLayout;
    private AppCompatButton nxtBtn;
    private AppCompatSpinner stateSelector,operator_select;
    private String subscriberName;
//    private AppCompatSpinner stateSelector;
    Bundle saveInstanceBundle;
    private String planKey,SelectedValue;
    // private int selectedSubscriber = -1;
    AppCompatTextView seePlans;
    private int subscriber = -1;
    public Spinner sppiner_service;
    private View vhView;
    private Call<ResponseGetPostpaidPlans> getPlansCall;

    public RechargeHomeFragment() {
        // Required empty public constructor
    }

    public static RechargeHomeFragment newInstance(String param1, String param2) {
         fragment = new RechargeHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static RechargeHomeFragment getInstance(){
        return fragment;
    }


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        saveInstanceBundle = new Bundle();
        if(context instanceof OnMobileRechargeListener) {
            mListener = (OnMobileRechargeListener)context;
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recharge_home, container, false);
        seePlans=view.findViewById(R.id.see_plans);
        sppiner_service=view.findViewById(R.id.sppiner_service);


        rechargeTypeGroup = view.findViewById(R.id.recharge_type_group);
        noServiceTxtView = view.findViewById(R.id.no_service_msg);
        amountLayout = view.findViewById(R.id.amount);
        mobile_hint=view.findViewById(R.id.mobile_hint);
        vc_hint=view.findViewById(R.id.vc_hint);

        recyclerView = view.findViewById(R.id.services_list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        subscriberModules.clear();

        stateSelector = view.findViewById(R.id.state_select);
        operator_select = view.findViewById(R.id.operator_select);

        rechargeTypeGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        try {
            if (subscriber != -1 && vhView != null){
                new Handler().postDelayed(() -> {
                    Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(subscriber)).itemView.setAlpha(1f);
                    mListener.getMobileRechargeModule().setPreOperator(subscriberModules.get(subscriber).getKey());
                    mListener.getMobileRechargeModule().setImage(subscriberModules.get(subscriber).getImage());
                },100);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        selectService();
        return view;
    }

    private void selectAction(String selectedValue) {
        if(selectedValue.equalsIgnoreCase("D")){
            operator_select.setVisibility(View.VISIBLE);
            vc_num.setVisibility(View.VISIBLE);
            mobileRechargeNum.setVisibility(View.GONE);
            rechargeTypeGroup.setVisibility(View.GONE);
            mobile_hint.setVisibility(View.GONE);
            vc_hint.setVisibility(View.VISIBLE);
            nxtBtn.setVisibility(View.VISIBLE);
            noServiceTxtView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            seePlans.setVisibility(View.GONE);
            amountLayout.setVisibility(View.VISIBLE);
            rechargeAmount.setText("");

        }else{
            operator_select.setVisibility(View.GONE);
            vc_num.setVisibility(View.GONE);
            mobileRechargeNum.setVisibility(View.VISIBLE);
            rechargeTypeGroup.setVisibility(View.VISIBLE);
            mobile_hint.setVisibility(View.VISIBLE);
            vc_hint.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            seePlans.setVisibility(View.VISIBLE);
        }

    }

    private void selectService() {
        String[] arraySpinner = new String[] {
                "Select Service type", "Mobile Recharge", "DTH"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sppiner_service.setAdapter(adapter);
        sppiner_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if(sppiner_service.getSelectedItem().toString().equalsIgnoreCase("Mobile Recharge")){
                    SelectedValue="M";

                }else if(sppiner_service.getSelectedItem().toString().equalsIgnoreCase("DTH")){
                    SelectedValue="D";

                }
                if(sppiner_service.getSelectedItem().toString().equalsIgnoreCase("Select service type"))
                    SelectedValue="";
                // Toast.makeText(ActivityElectricity.this, sppiner_opertor.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                selectAction(SelectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(null != getPlansCall) {
            getPlansCall.cancel();
        }
    }

    private void displayPrepaidSubscriberList(){
        updateOperator();
        subscriberModules.clear();
        rechargeAmount.setText("");
        subscriberModules = mListener.getMobileRechargeModule().getPrepaidSubscriberList();
        if(mListener.getMobileRechargeModule().getSelectedSubscriber()!=-1) {
            subscriberModules.get(mListener.getMobileRechargeModule().getSelectedSubscriber()).setSelected(true);
        }
        SubscriberListAdapter listRecyclerView = null;

        if (listRecyclerView == null) {
            listRecyclerView = new SubscriberListAdapter((chosenSubscriber, clickedItemViewHolder) -> onClick(chosenSubscriber, clickedItemViewHolder), getContext(),
                    subscriberModules);
            recyclerView.setAdapter(listRecyclerView);
        } else {
            listRecyclerView.notifyDataSetChanged();
        }

    }
    private void displayDTHSubscriberList(){
        updateOperator();
        subscriberModules.clear();
        rechargeAmount.setText("");
        subscriberModules = mListener.getMobileRechargeModule().getPrepaidSubscriberList();
        if(mListener.getMobileRechargeModule().getSelectedSubscriber()!=-1) {
            subscriberModules.get(mListener.getMobileRechargeModule().getSelectedSubscriber()).setSelected(true);
        }
        SubscriberListAdapter listRecyclerView = null;

        if (listRecyclerView == null) {
            listRecyclerView = new SubscriberListAdapter((chosenSubscriber, clickedItemViewHolder) -> onClick(chosenSubscriber, clickedItemViewHolder), getContext(),
                    subscriberModules);
            recyclerView.setAdapter(listRecyclerView);
        } else {
            listRecyclerView.notifyDataSetChanged();
        }

    }
    private void displayPostpaidSubscriberList() {

        updateOperator();
        rechargeAmount.setText("");

        subscriberModules.clear();
        subscriberModules.add(new SubscriberModule(0, R.drawable.airtel, "AirtelExpress","AB", "Airtel"));
        subscriberModules.add(new SubscriberModule(1, R.drawable.reliance,"Reliance", "RB",""));
        subscriberModules.add(new SubscriberModule(2, R.drawable.bsnl, "BSNL", "BB", "BSNL"));
        subscriberModules.add(new SubscriberModule(3, R.drawable.idea,  "Idea", "IB","idea"));
        subscriberModules.add(new SubscriberModule(4, R.drawable.vodafone,  "Vodafone", "VB", "Vodafone"));
        subscriberModules.add(new SubscriberModule(5,R.drawable.indicom,"Tata Indicom", "TB", "Tata Indicom"));
//        subscriberModules.add(new SubscriberModule(6,R.drawable.jio,"JOE","JOE","jio"));
        try {
            if(mListener.getMobileRechargeModule().getSelectedSubscriber()!=-1) {
                subscriberModules.get(mListener.getMobileRechargeModule().getSelectedSubscriber()).setSelected(true);
            }
        } catch (Exception e) { }
        SubscriberListAdapter listRecyclerView = new SubscriberListAdapter(this::onClick,getContext(), subscriberModules);
        recyclerView.setAdapter(listRecyclerView);
    }

    private void updateOperator() {
        recyclerView.setVisibility(View.VISIBLE);
        noServiceTxtView.setVisibility(View.GONE);
        amountLayout.setVisibility(View.VISIBLE);
        nxtBtn.setVisibility(View.VISIBLE);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nxtBtn = view.findViewById(R.id.mobile_recharge_nxt_btn);
        nxtBtn.setOnClickListener(this::onClick);
        seePlans = view.findViewById(R.id.see_plans);
        seePlans.setOnClickListener(this);
        mobileRechargeNum = view.findViewById(R.id.mobile_recharge_num);
//        mobileRechargeNum.setText(BuildConfig.USERNAME);

        rechargeAmount = view.findViewById(R.id.recharge_amount);
        vc_num = view.findViewById(R.id.vc_num);

    }


    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.recharge_type_prepaid:
                    racType = getResources().getString(R.string.prepaid_radio_btn);
                    seePlans.setVisibility(View.VISIBLE);
                    mListener.getMobileRechargeModule().setPlanType("Prepaid Mobile");
                    mListener.getMobileRechargeModule().setRechargeType("0");
                    saveInstanceBundle.putInt("recharge_type_id",R.id.recharge_type_prepaid);
                    displayPrepaidSubscriberList();
                break;
            case R.id.recharge_type_postpaid:
                    racType = getResources().getString(R.string.postpaid_radio_btn);
                    seePlans.setVisibility(View.GONE);
                    mListener.getMobileRechargeModule().setRechargeType("1");
                    mListener.getMobileRechargeModule().setPlanType("Postpaid Mobile");
                    saveInstanceBundle.putInt("recharge_type_id",R.id.recharge_type_postpaid);
                    displayPostpaidSubscriberList();
                break;
        }
    }




    public void onClick(View view) {

        int id = view.getId();
        String phone = Objects.requireNonNull(mobileRechargeNum.getText()).toString();
        String amount = Objects.requireNonNull(rechargeAmount.getText()).toString();

        switch (id){
            case R.id.mobile_recharge_nxt_btn:
                if(SelectedValue.equalsIgnoreCase("M")){
                    if(!isValidMobile(phone)) {
                        showDialog("Sorry!!", getString(R.string.phone_number_check_msg));
                    }else if (rechargeTypeGroup.getCheckedRadioButtonId() == -1){
                        showDialog("Sorry!!", getString(R.string.recharge_type_chk_msg));
                    } else if(subscriber == -1) {
                        showDialog("Sorry!!", getString(R.string.mobile_operator_check_msg));
                    } else if (String.valueOf(stateSelector.getSelectedItem()).equals(getString(R.string.spinner_prompt))){
                        showDialog("Sorry!!", getString(R.string.spinner_prompt));
                    } else if(amount.isEmpty() || Integer.parseInt(amount) <= 9) {
                        showDialog("Sorry!!", getString(R.string.valid_recharge_amt_check_msg));
                    }
                    else {

                        mListener.getMobileRechargeModule().setMobileNumber(phone);
                        mListener.getMobileRechargeModule().setRechargeAmount(amount);
                        mListener.getMobileRechargeModule().setRecType(racType);

                        if(racType.equals(getString(R.string.prepaid_radio_btn))){
                            mListener.getMobileRechargeModule().setPlanType("Prepaid Mobile");
                            mListener.getMobileRechargeModule().setRechargeType("0");
                        } else if (racType.equals(getString(R.string.postpaid_radio_btn))){
                            mListener.getMobileRechargeModule().setRechargeType("1");
                            mListener.getMobileRechargeModule().setPlanType("Postpaid Mobile");
                        }

                        mListener.getMobileRechargeModule().setStateName(String.valueOf(stateSelector.getSelectedItem()));
                        mListener.getMobileRechargeModule().setMobileOperator(subscriberModules.get(subscriber).getName());
                        mListener.getMobileRechargeModule().setImage(subscriberModules.get(subscriber).getImage());
                        mListener.getMobileRechargeModule().setUserID(MyProfile.getInstance().getUserID());
                        BigDecimal balance = new BigDecimal(MyProfile.getInstance().getUserID());
                        BigDecimal rechargeAmount = new BigDecimal(mListener.getMobileRechargeModule().getRechargeAmount());
                  /*  if(balance.compareTo(rechargeAmount) >= 0) {
                        mListener.goToMakePaymentFragment();
                    } else  {
                        showDialog("", "Insufficient balance in your wallet.");
                    }*/
                        mListener.goToMakePaymentFragment();
                    }
                }else if(SelectedValue.equalsIgnoreCase("")){
                    showDialog("Sorry!!", "please Select Service");

                }
                else{
                    if(vc_num.getText().toString().equalsIgnoreCase("")){
                        showDialog("Sorry!!", "please Enter Valid Consumer Number");

                    }else if (String.valueOf(stateSelector.getSelectedItem()).equals(getString(R.string.spinner_prompt))){
                        showDialog("Sorry!!", getString(R.string.spinner_prompt));
                    }else if(operator_select.getSelectedItem().equals(getString(R.string.spinner_dth))) {
                        showDialog("Sorry!!", getString(R.string.mobile_operator_check_msg));
                    }
                    else if(amount.isEmpty() || Integer.parseInt(amount) <= 9) {
                        showDialog("Sorry!!", getString(R.string.valid_recharge_amt_check_msg));
                    }
                    else if(vc_num.getText().toString().isEmpty() || Integer.parseInt(vc_num.getText().toString()) <= 9) {
                        showDialog("Sorry!!", "please Enter Valid Consumer Number");
                    }else{
                        mListener.getMobileRechargeModule().setMobileNumber(vc_num.getText().toString());
                        mListener.getMobileRechargeModule().setRechargeAmount(amount);
                        mListener.getMobileRechargeModule().setRechargeType("2");;
                        mListener.getMobileRechargeModule().setRecType("DTH");
                        mListener.getMobileRechargeModule().setPreOperator("DTH");
                        mListener.getMobileRechargeModule().setStateName(String.valueOf(stateSelector.getSelectedItem()));
                        mListener.getMobileRechargeModule().setMobileOperator(operator_select.getSelectedItem().toString());
                       String key= getoperatorCode(operator_select.getSelectedItem().toString());
                        mListener.getMobileRechargeModule().setPreOperator(key);
                        mListener.goToMakePaymentFragment();

                    }

                }

                break;

            case R.id.see_plans:
                MobileRechargeService rechargeService = RetrofitClientInstance.getRetrofitInstanceRokad().create(MobileRechargeService.class);
                int j = mListener.getMobileRechargeModule().getPreOperator().length();
                if (subscriberName != null && stateSelector!= null && rechargeTypeGroup!=null && mListener != null) {
                    if (subscriberName.isEmpty() && stateSelector.getSelectedItem().equals(getString(R.string.spinner_prompt))
                            && rechargeTypeGroup.getCheckedRadioButtonId() == -1) {
                        showDialog("Sorry!!", "Please choose your Mobile Operator, your State, Recharge type above.");
                    }
                    if(phone.equalsIgnoreCase("")){
                        showDialog("Sorry!!", "please Enter Valid Consumer Number");

                    }else if (!isValidMobile(phone)) {
                        showDialog("Sorry!!", getString(R.string.phone_number_check_msg));
                    } else if (rechargeTypeGroup.getCheckedRadioButtonId() == -1) {
                        showDialog("Sorry!!", getString(R.string.recharge_type_chk_msg));
                    } else if (subscriber == -1) {
                        showDialog("Sorry!!", getString(R.string.mobile_operator_check_msg));
                    } else if (String.valueOf(stateSelector.getSelectedItem()).equals(getString(R.string.spinner_prompt))) {
                        showDialog("Sorry!!", getString(R.string.spinner_prompt));
                    } else {

                        ProgressDialog progressBar = new ProgressDialog(getActivity(), R.style.mySpinnerTheme);
                        progressBar.setCancelable(false);
                        progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                        progressBar.show();

                        // String rechargeType = racType.equals(getString(R.string.prepaid_radio_btn)) ? "P" : "PO";
                        if (racType.equals(getString(R.string.prepaid_radio_btn))) {
                            String state = stateSelector.getSelectedItem().toString();
                            Call<ResponseGetPlans> getPlansCall = rechargeService.getPrepaidPlans(planKey,
                                    state,"P", BuildConfig.MOBILE_APPLICATION, BuildConfig.MOBILE_VERSION_ID);
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
                                        getSpecialPlans(phone, rechargeService, progressBar, records);
                                    } else {
                                        showDialog("Sorry!!", "Looks like there's a network or server problem. Please try again in sometime.");
                                    }
                                }


                                public void onFailure(Call<ResponseGetPlans> call, Throwable t) {
                                    progressBar.dismiss();
                                    showDialog("Sorry!!", "Plans are not available");
                                }
                            });
                        }
                    }
                } else {
                    showDialog("Sorry!!", "Please fill in Subscriber and State details to see the plans.");
                }

                break;
            default:
                throw new UnsupportedOperationException("Don't know where you've clicked");
        }
    }

    private String getoperatorCode(String s) {

        String key="";
        switch (s){
            case "Airtel dth":
                key="AD";
                break;
            case "Big TV":
                key="BD";
                break;
            case "Dish TV":
                key="DT";
                break;
            case "Tata Sky":
                key="TS";
                break;
            case "Videocon":
                key="VD";
                break;
            case "Sun Direct":
                key="ST";
                break;

        }
        return key;
    }

    private void getSpecialPlans(String phone, MobileRechargeService rechargeService, ProgressDialog progressBar, Records records) {
        getPlansCall = rechargeService.getPostpaidPlans(planKey, stateSelector.getSelectedItem().toString(),
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

                    mListener.goToSeePlansFragment(records);
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

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("New Recharge");
        rechargeAmount.setText(mListener.getMobileRechargeModule().getRechargeAmount());
//        if (selectedSubscriber != -1) {
//            SubscriberListAdapter datapter = (SubscriberListAdapter) recyclerView.getAdapter();
//            datapter.
//        }
    }

    @Override
    public void onClick(int chosenSubscriber, View view) {
        mListener.getMobileRechargeModule().setSelectedSubscriber(chosenSubscriber);

//        if(null != selectedView) {
//            selectedView.setAlpha(.5f);
//        }
//        selectedView = v;
//        if(selected != position) {
//            subscriberModules.get(selected).setSelected(false);
//        }

        if (subscriber == chosenSubscriber){
            recyclerView.findViewHolderForAdapterPosition(subscriber).itemView.setAlpha(.5f);
            subscriberModules.get(subscriber).setSelected(!subscriberModules.get(chosenSubscriber).isSelected());
            subscriber = -1;
        }

        subscriberModules.get(chosenSubscriber).setSelected(!subscriberModules.get(chosenSubscriber).isSelected());

        if(subscriberModules.get(chosenSubscriber).isSelected()) {
            view.setAlpha(1f);
            vhView = view;
            subscriber = chosenSubscriber;
            subscriberName = subscriberModules.get(chosenSubscriber).getName();
            subscriberModules.get(chosenSubscriber);
            planKey = subscriberModules.get(chosenSubscriber).getPlanKey();
            mListener.getMobileRechargeModule().setPreOperator(subscriberModules.get(chosenSubscriber).getKey());
            mListener.getMobileRechargeModule().setImage(subscriberModules.get(chosenSubscriber).getImage());
            mListener.getMobileRechargeModule().setMobileOperator(subscriberName);
        } else {
            view.setAlpha(.5f);
            vhView = view;
            subscriber = -1;
            rechargeAmount.setText("");
            subscriberName = "";
            mListener.getMobileRechargeModule().setPreOperator("");
            mListener.getMobileRechargeModule().setMobileOperator("");
        }
    }

}
