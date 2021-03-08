package com.rmart.customerservice.mobile.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.customerservice.mobile.adapters.RechargeHistoryRecyclerAdapter;
import com.rmart.customerservice.mobile.api.MobileRechargeService;
import com.rmart.customerservice.mobile.interfaces.OnMobileRechargeListener;
import com.rmart.customerservice.mobile.models.LastTransaction;
import com.rmart.customerservice.mobile.models.ResponseGetHistory;
import com.rmart.customerservice.mobile.models.SubscriberModule;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.RetrofitClientInstance;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileRechargeHistoryFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private AppCompatTextView emptyView;
    private OnMobileRechargeListener mListener;
    private ProgressBar progressSpinner;

    public MobileRechargeHistoryFragment() {
        // Required empty public constructor
    }

    public static MobileRechargeHistoryFragment newInstance(String param1, String param2) {
        MobileRechargeHistoryFragment fragment = new MobileRechargeHistoryFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnMobileRechargeListener) {
            mListener = (OnMobileRechargeListener)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mobile_recharge_history, container, false);
        emptyView = view.findViewById(R.id.empty_view);
         progressSpinner = view.findViewById(R.id.progress_circular);
        return view;
    }

    public void loadRechargeHistory(){
        progressSpinner.setVisibility(View.VISIBLE);
        MobileRechargeService mobileRechargeService = RetrofitClientInstance.getRetrofitInstance().create(MobileRechargeService.class);
        mobileRechargeService.getHistory(MyProfile.getInstance().getUserID()).enqueue(new Callback<ResponseGetHistory>() {
            @Override
            public void onResponse(Call<ResponseGetHistory> call, Response<ResponseGetHistory> response) {
                progressSpinner.setVisibility(View.GONE);
                if (getActivity()!= null && isAdded()) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("success") && response.body().getLastTransaction().length > 0) {
//                        progressBar.dismiss();
                            progressSpinner.setVisibility(View.GONE);
                            emptyView.setVisibility(View.GONE);
                            for (LastTransaction lastTransaction : response.body().getLastTransaction()) {
                                SubscriberModule operator = mListener.getMobileRechargeModule().getPrepaidSubscriber(lastTransaction.getOperator());
                                lastTransaction.setOperatorLogo(operator.getImage());
                                lastTransaction.setOperatorName(operator.getName());
                                lastTransaction.setStateName(lastTransaction.getStateName());
                            }

                            RechargeHistoryRecyclerAdapter recyclerAdapter = new RechargeHistoryRecyclerAdapter(response.body().getLastTransaction()
                                    ,MobileRechargeHistoryFragment.this);
                            recyclerView.setAdapter(recyclerAdapter);
                        } else {
                            showDialog("", response.message());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetHistory> call, Throwable t) {
                try {
                    progressSpinner.setVisibility(View.GONE);
                    Activity activity = getActivity();
                    if (activity != null && isAdded()) {
                        if (t instanceof SocketTimeoutException) {
                            showDialog(getString(R.string.time_out_title), getString(R.string.time_out_msg));
                        } else {
                            showDialog("Sorry..!!", getString(R.string.server_failed_case));
                            Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setText("Please try again later.");
                        emptyView.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("Exception", "Exception: "+e.getMessage());
                }
            }
        });
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (!visible) {
            try {
                mListener.resetMobileRechargeModule();
            } catch (Exception e) {

            }
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recharge_history);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Mobile Recharge");
        loadRechargeHistory();
        recyclerView.setAdapter(null);
    }

    @Override
    public void onClick(View view) {
        LastTransaction data = (LastTransaction) view.getTag();
        SubscriberModule operator = mListener.getMobileRechargeModule().getPrepaidSubscriber(data.getOperator());
        mListener.getMobileRechargeModule().setMobileNumber(data.getRechargeOn());
        mListener.getMobileRechargeModule().setPlanType("Prepaid Mobile");
        mListener.getMobileRechargeModule().setRechargeType("0");
        mListener.getMobileRechargeModule().setStateName(data.getStateName());
        mListener.getMobileRechargeModule().setPreOperator(operator.getKey());
        mListener.getMobileRechargeModule().setImage(operator.getImage());
        mListener.getMobileRechargeModule().setMobileOperator(operator.getName());
        mListener.getMobileRechargeModule().setRechargeAmount(data.getLastTransactionAmount());
        mListener.getMobileRechargeModule().setUserID(MyProfile.getInstance().getUserID());
        mListener.goToMakePaymentFragment();
    }
}
