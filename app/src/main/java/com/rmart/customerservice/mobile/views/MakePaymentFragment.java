package com.rmart.customerservice.mobile.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.interfaces.OnMobileRechargeListener;
import com.rmart.customerservice.mobile.models.MRechargeBaseClass;
import com.rmart.customerservice.mobile.models.MobileRecharge;
import com.rmart.customerservice.mobile.models.ResponseMobileRecharge;
import com.rmart.customerservice.mobile.models.RokadPaymentRequest;
import com.rmart.electricity.api.ElecticityService;
import com.rmart.electricity.ElectricityCcavenue;
import com.rmart.electricity.rsakeyResponse;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MakePaymentFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnMobileRechargeListener mListener;
    private Call<ResponseMobileRecharge> user;
    ElecticityService eleService;
    RokadPaymentRequest rp;
    public MakePaymentFragment() {
        // Required empty public constructor
    }

    public static MakePaymentFragment newInstance(String param1, String param2) {
        MakePaymentFragment fragment = new MakePaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnMobileRechargeListener) {
            mListener = (OnMobileRechargeListener)context;
        }
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
        requireActivity().setTitle("Make Payment");
        return inflater.inflate(R.layout.fragment_make_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MobileRecharge data = mListener.getMobileRechargeModule();
        ((AppCompatImageView)view.findViewById(R.id.service_provider_img)).setImageResource(data.getImage());
        ((AppCompatTextView)view.findViewById(R.id.mobile_num)).setText(data.getMobileNumber());
        ((AppCompatTextView)view.findViewById(R.id.subscriber_name)).setText(data.getMobileOperator());
        ((AppCompatTextView)view.findViewById(R.id.state_name)).setText(data.getStateName());
        ((AppCompatTextView)view.findViewById(R.id.recharge_amt)).setText(data.getRechargeAmount());
        ((AppCompatTextView)view.findViewById(R.id.total_amt)).setText(data.getRechargeAmount());
        view.findViewById(R.id.payment_btn).setOnClickListener(this);
        view.findViewById(R.id.money_to_wallet_btn).setOnClickListener(this);
    }

    private void doToRecharge() {

        ProgressDialog progressBar = new ProgressDialog(getActivity(), R.style.mySpinnerTheme);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressBar.show();

        MobileRecharge data = mListener.getMobileRechargeModule();
        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstance().create(MobileRechargeService.class);
        user = getResponseMobileRechargeCall(data, mobileRechargeService);
        user.enqueue(new Callback<ResponseMobileRecharge>() {
            @Override
            public void onResponse(Call<ResponseMobileRecharge> call, Response<ResponseMobileRecharge> response) {
                Log.d("onResponse", "onResponse: ");
                if (response.body().getStatus().equalsIgnoreCase("Failed")) {
//                    Toast.makeText(getContext(), BuildConfig.BASE_URL +BuildConfig.RECHARGE ,Toast.LENGTH_LONG).show();
                    String bod = response.body().getMsg();
                    if (bod.isEmpty()){
                        bod = "Transaction Failed. Please try again after sometime.";
                    }
                    showDialog(response.body().getStatus(), bod);
                    progressBar.dismiss();
                } else {
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    Fragment prev = requireActivity().getSupportFragmentManager().findFragmentByTag("RechargeDialogFragment");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    DialogFragment dialogFragment;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        //dialogFragment = RechargeDialogFragment.newInstance(true, "");
                    } else {
                        //dialogFragment = RechargeDialogFragment.newInstance(false, "");
                    }
                    //dialogFragment.setCancelable(false);
                    //dialogFragment.show(ft, "dialog fragment");
                    progressBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseMobileRecharge> call, Throwable t) {
                Log.d("onFailure", "onFailure: ");
                try {
                    if(t instanceof SocketTimeoutException){
                        showDialog(getString(R.string.time_out_title), getString(R.string.time_out_msg));
                    } else if (call.isCanceled()) {
                        Log.e("TAG", "request was cancelled");
                    } else {
                        showDialog("Sorry..!!", getString(R.string.server_failed_case));
                        Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.cancel();
                } catch (Exception e) {
                    Log.d("Exception", "Exception: "+e.getMessage());
                }
                /*FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = requireActivity().getSupportFragmentManager().findFragmentByTag("vehicleLockFragment");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = RechargeDialogFragment.newInstance(false, "");
                dialogFragment.setCancelable(false);
                dialogFragment.show(getChildFragmentManager(), "dialog fragment");
                progressBar.dismiss();*/
            }
        });
    }

    private Call<ResponseMobileRecharge> getResponseMobileRechargeCall(MobileRecharge data, MobileRechargeService mobileRechargeService) {
        if(data.getRecType().equals(getResources().getString(R.string.postpaid_radio_btn))) {
            return mobileRechargeService.postPaidRecharge(
                    data.getRechargeFrom(),
                    data.getPlanType(),
                    data.getService(),
                    data.getPreOperator(),
                    data.getMobileNumber(),
                    data.getRechargeAmount(),
                    data.getUserID(),
                    data.getMobileOperator(),
                    data.getRecType(),
                    data.getRechargeType(),
                    data.getMobileApp(),
                    data.getMobileAppVersionId(),
                    data.getPaymentType());
        } else {
            return mobileRechargeService.prePaidRecharge(
                    data.getRechargeFrom(),
                    data.getPlanType(),
                    data.getService(),
                    data.getPreOperator(),
                    data.getMobileNumber(),
                    data.getRechargeAmount(),
                    data.getUserID(),
                    data.getMobileOperator(),
                    data.getRecType(),
                    data.getRechargeType(),
                    data.getMobileApp(),
                    data.getMobileAppVersionId(),
                    data.getPaymentType());
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(user!=null) {
            user.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.payment_btn) {
           // doToRecharge();
            getRsaKey();
        } else if(view.getId() == R.id.money_to_wallet_btn) {
            showDialog("Sorry....", "Please wait, this feature will available soon");
        }
    }


    public void getRsaKey(){
        if (Utils.isNetworkConnected(getContext())) {


            ProgressDialog progressBar = new ProgressDialog(getContext(), R.style.mySpinnerTheme);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressBar.show();
            MobileRecharge mdata = mListener.getMobileRechargeModule();
            eleService = RetrofitClientInstance.getRetrofitInstance().create(ElecticityService.class);


                eleService.electicityProcessRsaKeyVRecharge(MyProfile.getInstance().getUserID(),mdata.getRechargeAmount(),"31","V_RECHARGE")
                    // eleService.electicityProcessRsaKey("524","160","1","electricity","123124")

                    .enqueue(new Callback<rsakeyResponse>() {
                        @Override
                        public void onResponse(Call<rsakeyResponse> call, Response<rsakeyResponse> response) {
                            progressBar.cancel();
                            rsakeyResponse data = response.body();
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                Intent ii= new Intent(getContext(), ServicePaymentActivity.class);
                                ii.putExtra("rsakeyresonse",  data.getData());
                                ii.putExtra("mobile_number",mdata.getMobileNumber());
                                ii.putExtra("operator",mdata.getMobileOperator());
                                if(mdata.getRecType().equals(getResources().getString(R.string.postpaid_radio_btn))) {
                                    RokadPaymentRequest rp = new RokadPaymentRequest();
                                    rp.setCustomerNumber("");
                                    rp.setMobileNumber((mdata.getMobileNumber()));
                                    rp.setPostOperator(mdata.getPreOperator());
                                    rp.setPreOperator("");
                                    rp.setRechargeAmount((mdata.getRechargeAmount()));
                                    rp.setServicetype(1);
                                    rp.setRechargetype("2");
                                    rp.setPreOperatorDth("");
                                    rp.setLocation(mdata.getStateName());
                                    rp.setRechargeTypeRegular("1");
                                    ii.putExtra("details",rp);
                                   // CcavenueRequestData(rp);

                                }else if(mdata.getRecType().equals(getResources().getString(R.string.prepaid_radio_btn))){
                                    rp = new RokadPaymentRequest();
                                    rp.setCustomerNumber("");
                                    rp.setMobileNumber((mdata.getMobileNumber()));
                                    rp.setPostOperator("");
                                    rp.setPreOperator(mdata.getPreOperator());
                                    rp.setRechargeAmount((mdata.getRechargeAmount()));
                                    rp.setServicetype(1);
                                    rp.setRechargetype((mdata.getRechargeType()));
                                    rp.setPreOperatorDth("");
                                    rp.setLocation(mdata.getStateName());
                                    rp.setRechargeTypeRegular("1");
                                    ii.putExtra("details",rp);
                                    //CcavenueRequestData(rp);
                                }
                                else {
                                   rp = new RokadPaymentRequest();
                                    rp.setVc_number(mdata.getVcNumber());
                                    rp.setMobileNumber("");
                                    rp.setPostOperator("");
                                    rp.setPreOperator("");
                                    rp.setRechargeAmount((mdata.getRechargeAmount()));
                                    rp.setServicetype(2);
                                    rp.setRechargetype("");
                                    rp.setPreOperatorDth(mdata.getPreOperator());
                                    rp.setRechargeTypeRegular("1");
                                    rp.setLocation(mdata.getStateName());
                                    ii.putExtra("details",rp);
                                   // CcavenueRequestData(rp);

                                }

                                startActivity(ii);

                            } else {
                                showDialog("", response.body().getMsg());
                            }

                            progressBar.cancel();
                        }

                        @Override
                        public void onFailure(Call<rsakeyResponse> call, Throwable t) {
                            showDialog("", t.getMessage());
                            progressBar.cancel();
                        }
                    });
        }

    }
    public void CcavenueRequestData( RokadPaymentRequest paymentrp){

        if (Utils.isNetworkConnected(getActivity())) {

            ProgressDialog progressBar = new ProgressDialog(getActivity(), R.style.mySpinnerTheme);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressBar.show();


            ArrayList<String> ccabledata = new ArrayList<>();
            ccabledata.add("[{\"amount\":\"160.00\",\"bank_ref_no\":\"1614605172164\",\"billing_address\":\"Ffg\",\"billing_city\":\"Ttt\",\"billing_country\":\"India\",\"billing_email\":\"shwetadalvi9@gmail.com\",\"billing_name\":\"Shweta Dalvi\",\"billing_state\":\"Tt\",\"billing_tel\":\"8446399429\",\"billing_zip\":\"Rtt\",\"card_name\":\"AvenuesTest\",\"client_id\":\"2\",\"created_by\":\"0\",\"delivery_address\":\"Ffg\",\"delivery_city\":\"Ttt\",\"delivery_country\":\"India\",\"delivery_name\":\"Shweta Dalvi\",\"delivery_state\":\"Tt\",\"delivery_tel\":\"8446399429\",\"delivery_zip\":\"Rtt\",\"discount_value\":\"0.0\",\"failure_message\":\"\",\"mer_amount\":\"160.00\",\"message\":\"Thank you for shopping with us. Your transaction is successful.\",\"messageheading\":\"Transaction Successful\",\"order_id\":\"924\",\"order_status\":\"Success\",\"payment_mode\":\"Net Banking\",\"response_code\":\"0\",\"service_id\":7,\"service_order_id\":1031860,\"status_code\":\"null\",\"status_message\":\"Y\",\"tracking_id\":\"310006951692\",\"trans_date\":\"2021-03-01 18:56:59\"");

            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(ccabledata, new TypeToken<List<ElectricityCcavenue>>() {
            }.getType());

            if (!element.isJsonArray()) {
                // fail appropriately
                throw new NullPointerException();
            }
            JsonArray ccavenuejsonArray = element.getAsJsonArray();
            MobileRechargeService mService = RetrofitClientInstance.getInstance().getRetrofitInstanceRokad().create(MobileRechargeService.class);
            mService.VRecharge(paymentrp.getServicetype(),paymentrp.getPreOperatorDth(),paymentrp.getVc_number(),
                    paymentrp.getRechargetype(),paymentrp.getPreOperator(),paymentrp.getPostOperator(),paymentrp.getLocation(),
                    paymentrp.getMobileNumber(),paymentrp.getRechargeTypeRegular(),paymentrp.getRechargeAmount(),MyProfile.getInstance().getUserID(),ccavenuejsonArray.toString())
                    .enqueue(new Callback<MRechargeBaseClass>() {
                        @Override
                        public void onResponse(Call<MRechargeBaseClass> call, Response<MRechargeBaseClass> response) {

                            progressBar.cancel();
                            MRechargeBaseClass datap = response.body();
                            if (datap.getStatus().equalsIgnoreCase("success")) {
                                 Toast.makeText(getActivity(), "Payment success"+datap, Toast.LENGTH_LONG).show();

                                //setData(data);
                               // openDialog1(datap.getData());

                            } else {
                                showDialog("", datap.getMsg());
                                //Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();
                                Intent ii = new Intent(getActivity(), MobileRechargeActivity.class);
                                startActivity(ii);
                               // finishAffinity();
                            }

                            progressBar.cancel();
                        }

                        @Override
                        public void onFailure(Call<MRechargeBaseClass> call, Throwable t) {
                            // Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();
                            showDialog("", t.getMessage());
                            progressBar.cancel();
                            // Toast.makeText(getApplicationContext(), "No bill data available", Toast.LENGTH_LONG).show();
                            Intent ii = new Intent(getActivity(), MobileRechargeActivity.class);
                            startActivity(ii);
                            //finishAffinity();
                        }
                    });

        }else{
            showDialog("Sorry!!", getString(R.string.error_internet));
            Intent ii = new Intent(getActivity(), MobileRechargeActivity.class);
            startActivity(ii);
           // finishAffinity();
        }

    }


}
