package com.rmart.wallet.billing_history.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rmart.R;
import com.rmart.baseclass.views.BaseFragment;
import com.rmart.databinding.FragmentBillingHistoryBinding;
import com.rmart.profile.model.MyProfile;
import com.rmart.wallet.billing_history.adapter.BillHistoryAdapter;
import com.rmart.wallet.billing_history.models.BillingResponse;
import com.rmart.wallet.billing_history.models.Datum;
import com.rmart.wallet.billing_history.repository.BillingRepository;
import com.rmart.wallet.billing_history.viewmodels.BillingServicemodule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillingHistoryFragment extends BaseFragment {
    Toolbar toolbar;
    Context context;
    BillingServicemodule mViewModel;

    public BillingHistoryFragment() {
        // Required empty public constructor
    }

    public static BillingHistoryFragment getInstance() {
        BillingHistoryFragment billingHistoryFragment = new BillingHistoryFragment();
        Bundle extras = new Bundle();
        billingHistoryFragment.setArguments(extras);
        return billingHistoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_billing_history, container, false);*/
        FragmentBillingHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_billing_history, container, false);
        binding.btnGetDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar1.setVisibility(View.VISIBLE);
                BillingRepository.getBillingHistory(binding.fromDateTextView.getText().toString().trim(),binding.toDateTextView.getText().toString(), MyProfile.getInstance(getActivity()).getUserID()).observeForever(billingResponse -> {
                    ArrayList<Datum> datumArrayList = (ArrayList<Datum>) billingResponse.getData();
                    if (billingResponse.getStatus()==200){
                        binding.progressBar1.setVisibility(View.GONE);
                        BillHistoryAdapter billHistoryAdapter = new BillHistoryAdapter(getContext(),datumArrayList);
                        binding.setBillingAdapter(billHistoryAdapter);
                        binding.billrecyclerView.setVisibility(View.VISIBLE);
                    }

                });
            }
        });
        binding.fromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();
                if (getFragmentManager() != null)
                    dialogFragment.show(getFragmentManager(), "Date Picker");

            }
        });
        binding.toDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new TODatePickerFragment();
                if (getFragmentManager() != null)
                    dialogFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
    }

    public static class TODatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            final Calendar calendar = Calendar.getInstance();
            String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

            view.setMaxDate(calendar.getTimeInMillis());

            long returnedYear = calendar.get(Calendar.YEAR) - 1940;
            Log.d("returnedYear", returnedYear + " ");

            TextView to_date = getActivity().findViewById(R.id.to_date_textView);
            to_date.setText(formattedDate);
            Log.d("DATE", year + " ");

            if (getActivity() != null) {

            }
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            final Calendar calendar = Calendar.getInstance();
            String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

            view.setMaxDate(calendar.getTimeInMillis());

            long returnedYear = calendar.get(Calendar.YEAR) - 1940;
            Log.d("returnedYear", returnedYear + " ");

            TextView form_date = getActivity().findViewById(R.id.from_date_textView);
            form_date.setText(formattedDate);
            Log.d("DATE", year + " ");


            if (getActivity() != null) {

            }
        }
    }
}