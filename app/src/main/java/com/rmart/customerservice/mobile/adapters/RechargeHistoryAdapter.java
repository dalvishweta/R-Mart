package com.rmart.customerservice.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.dashboard.adapters.HomeAdapter;
import com.rmart.customerservice.mobile.listners.HistoryClickListner;
import com.rmart.customerservice.mobile.models.LastTransaction;
import com.rmart.databinding.MobileHistoryBinding;
import com.rmart.databinding.ServicesBinding;


public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.HistoryViewHolder> {

    LastTransaction[] mLastTransaction;
    HistoryClickListner historyClickListner;
    public RechargeHistoryAdapter(LastTransaction[] lastTransaction, HistoryClickListner historyClickListner) {
        mLastTransaction = lastTransaction;
        this.historyClickListner = historyClickListner;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MobileHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.mobile_history, parent, false);
        HistoryViewHolder vh = new HistoryViewHolder(binding);
        return vh;


    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        LastTransaction data = mLastTransaction[position];
        holder.itemView.setOnClickListener(view -> historyClickListner.onSelect(data));
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mLastTransaction.length;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        MobileHistoryBinding binding;

        public HistoryViewHolder(MobileHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.LastTransaction, obj);
            binding.executePendingBindings();
        }
    }
}
