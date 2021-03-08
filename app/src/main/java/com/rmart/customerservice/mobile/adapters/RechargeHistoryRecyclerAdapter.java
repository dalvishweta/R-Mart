package com.rmart.customerservice.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customerservice.mobile.models.LastTransaction;


public class RechargeHistoryRecyclerAdapter extends RecyclerView.Adapter<RechargeHistoryRecyclerAdapter.HistoryHolder> {

    LastTransaction[] mLastTransaction;
    View.OnClickListener mOnClick;
    public RechargeHistoryRecyclerAdapter(LastTransaction[] lastTransaction, View.OnClickListener callback) {
        mLastTransaction = lastTransaction;
        mOnClick = callback;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recharge_history_list_item, parent, false);
        HistoryHolder historyHolder =  new HistoryHolder(view);
        historyHolder.repeatTransaction.setOnClickListener(mOnClick);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        LastTransaction data = mLastTransaction[position];
        holder.repeatTransaction.setTag(data);
        holder.subscriberImg.setImageResource(data.getOperatorLogo());
        holder.planPrice.setText("Rs."+data.getLastTransactionAmount());
        holder.stateName.setText(data.getStateName());
        holder.subscriberName.setText(data.getOperatorName());
        holder.mobileNum.setText(data.getRechargeOn());
    }

    @Override
    public int getItemCount() {
        return mLastTransaction.length;
    }

    static class HistoryHolder extends RecyclerView.ViewHolder {

        AppCompatImageView subscriberImg;
        AppCompatTextView mobileNum, subscriberName, stateName, planPrice, repeatTransaction;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            subscriberImg = itemView.findViewById(R.id.service_provider_img);
            mobileNum = itemView.findViewById(R.id.mobile_num);
            subscriberName = itemView.findViewById(R.id.subscriber_name);
            stateName = itemView.findViewById(R.id.state_name);
            planPrice = itemView.findViewById(R.id.plan_price);
            repeatTransaction = itemView.findViewById(R.id.repeat_transaction);
        }
    }
}
