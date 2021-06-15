package com.rmart.electricity.billdetails.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rmart.R;
import com.rmart.customerservice.mobile.fragments.PaymentStatusFragment;
import com.rmart.customerservice.mobile.models.mobileRecharge.Recharge;
import com.rmart.customerservice.mobile.models.mobileRecharge.RechargeBaseClass;
import com.rmart.databinding.FragmentBillDetailsBinding;
import com.rmart.electricity.CCavenueres;
import com.rmart.electricity.PaymentActivity;
import com.rmart.electricity.RSAKeyResponse;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.electricity.billdetails.modules.BillDetailsModule;
import com.rmart.electricity.fetchbill.model.BillDetails;
import com.rmart.electricity.paybill;
import com.rmart.electricity.selectoperator.model.Operator;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOBILENO = "param2";
    private static final String UNITS = "param3";
    private static final String BILLDETAILS = "param4";
    private static final String OPERATORS = "param1";
    CCavenueres reskdata;
    String  mobileno, units;
    BillDetails billDetails;
    Operator operator;
    public BillDetailsFragment() {
        // Required empty public constructor
    }


    public static BillDetailsFragment newInstance(String mobileno, String units, BillDetails billDetails, Operator operator) {
        BillDetailsFragment fragment = new BillDetailsFragment();
        Bundle args = new Bundle();
        args.putString(MOBILENO, mobileno);
        args.putString(UNITS, units);
        args.putSerializable(BILLDETAILS, billDetails);
        args.putSerializable(OPERATORS, operator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mobileno = getArguments().getString(MOBILENO);
            units = getArguments().getString(UNITS);
            billDetails = (BillDetails) getArguments().getSerializable(BILLDETAILS);
            operator = (Operator) getArguments().getSerializable(OPERATORS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FragmentBillDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_details, container, false);
        BillDetailsModule mViewModel = new ViewModelProvider(this).get(BillDetailsModule.class);
        mViewModel.isLoading.setValue(false);
        mViewModel.operatorMutableLiveData.postValue(operator);
        mViewModel.billDetailsMutableLiveData.postValue(billDetails);
        mViewModel.bill_unit.postValue(units);
        mViewModel.mobilenumber.postValue(mobileno);
        binding.setBillDetailsModule(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.rsakeyResponseMutableLiveData.observeForever(new Observer<RSAKeyResponse>() {
            @Override
            public void onChanged(RSAKeyResponse rsakeyResponse) {
                if (rsakeyResponse.getStatus().equalsIgnoreCase("Success")) {
                    reskdata=rsakeyResponse.getData();

                    if (rsakeyResponse.getData().getCcavenue() == 1) {
                        if (rsakeyResponse.getStatus().equalsIgnoreCase("success")) {
                            Intent ii = new Intent(getContext(), PaymentActivity.class);
                            ii.putExtra("rsakeyresonse", rsakeyResponse.getData());
                            ii.putExtra("cust_details", billDetails);
                            ii.putExtra("mobile_number", mobileno);
                            ii.putExtra("bill_unit", units);
                            ii.putExtra("operator", operator.slug);
                            getContext().startActivity(ii);


                        } else {
                            Toast.makeText(getContext(), "Server Response Failed", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        reskdata.setCcavenue(0);
                        reskdata.setWallet(true);
                        CCavenueres.CcavenueData cc= new CCavenueres.CcavenueData();
                        cc.setAmount(reskdata.getPayment_total_amount());
                        reskdata.setCcavenueData(cc);
                        CcavenueRequestData(rsakeyResponse.getData(), "null");
                    }

            }else{
                }
            }
        });


        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
        return binding.getRoot();
    }
    public void CcavenueRequestData(CCavenueres ccavenue_data, String html){

        if (Utils.isNetworkConnected(getContext())) {

            ProgressDialog progressBar = new ProgressDialog(getContext(), R.style.mySpinnerTheme);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressBar.show();
            Recharge recharge = new Recharge();
            RechargeBaseClass rs = new RechargeBaseClass();

            ElecticityService eleService = RetrofitClientInstance.getInstance().getRetrofitInstanceForAddProduct().create(ElecticityService.class);
            eleService.electicitybillProcess(MyProfile.getInstance(getContext()).getUserID(), operator.slug,String.valueOf(billDetails.getConsumerID()),units,
                    mobileno,String.valueOf(billDetails.getDueAmount()),billDetails.getConsumerName(),String.valueOf(billDetails.getOrderId()),html,ccavenue_data.getCcavenue(),ccavenue_data.getRokadOrderId(),ccavenue_data.isWallet())
                    .enqueue(new Callback<paybill>() {
                        @Override
                        public void onResponse(Call<paybill> call, Response<paybill> response) {

                            progressBar.cancel();
                            paybill datap = response.body();

                            if(datap!=null){
                                if (datap.getStatus()==200) {
                                    //Toast.makeText(getApplicationContext(), datap.getData().getDescription(), Toast.LENGTH_LONG).show();

                                    //setData(data);
                                    // openDialog1(datap);

                                    rs.setStatus(200);
                                    rs.setMsg("Transaction Successful");
                                    recharge.setAmount((ccavenue_data.getPayment_total_amount()));
                                    recharge.setTrackingId(String.valueOf(ccavenue_data.ccavenueData.getOrderId()));
                                    //  recharge.setMessage(datap.getData().getDescription());
                                    //  recharge.setBillingName(ob.getConsumerName());
                                   // recharge.setTransDate(ccAvenueResponse.getTransDate());
                                    // recharge.setServiceId((int) datap.getData().getConsumerID());


                                    rs.setData(recharge);
                                    displayStatus(rs);
                                }
                                else {
                                    rs.setStatus(400);
                                    rs.setMsg("Transaction Unsuccessful");
                                    recharge.setAmount((ccavenue_data.getPayment_total_amount()));
                                    recharge.setTrackingId(String.valueOf(ccavenue_data.ccavenueData.getOrderId()));
                                    rs.setData(recharge);
                                    displayStatus(rs);
                                }

                            }else{
                                rs.setStatus(400);
                                rs.setMsg("Transaction Unsuccessful");
                                recharge.setAmount((ccavenue_data.getPayment_total_amount()));
                                recharge.setTrackingId(String.valueOf(ccavenue_data.ccavenueData.getOrderId()));
                                rs.setData(recharge);
                                displayStatus(rs);
                            }


                            progressBar.cancel();
                        }

                        @Override
                        public void onFailure(Call<paybill> call, Throwable t) {
                            progressBar.cancel();
                            rs.setStatus(400);
                            rs.setMsg("Transaction Unsuccessful");
                            recharge.setAmount((ccavenue_data.getPayment_total_amount()));
                            recharge.setTrackingId(String.valueOf(ccavenue_data.ccavenueData.getOrderId()));
                            rs.setData(recharge);
                            displayStatus(rs);
                        }
                    });

        }else{
           // showDialog("Sorry!!", getString(R.string.error_internet));

        }

    }


    private void displayStatus(RechargeBaseClass paymentResponse) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, PaymentStatusFragment.newInstance(paymentResponse, null, null, paymentResponse.getData().getAmount()))
                .commit();

    }
}