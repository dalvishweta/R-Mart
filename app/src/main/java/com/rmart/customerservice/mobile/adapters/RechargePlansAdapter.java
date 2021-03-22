package com.rmart.customerservice.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customerservice.mobile.interfaces.OnPlanSelectedHandler;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;

import java.util.List;

public class RechargePlansAdapter extends RecyclerView.Adapter<RechargePlansAdapter.PlanHolder> {

    private OnPlanSelectedHandler mOnClickHandler;
    private List<RechargePlans> mRechargePlanList;

    public RechargePlansAdapter(OnPlanSelectedHandler mOnClickHandler, Context mContext, List<RechargePlans> rechargePlanList) {
        this.mOnClickHandler = mOnClickHandler;
        this.mRechargePlanList = rechargePlanList;
    }

    @NonNull
    @Override
    public PlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recharge_plan_item,parent, false);
        PlanHolder viewObject = new PlanHolder(view);

        return viewObject;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanHolder holder, int position) {
        holder.desc.setText(mRechargePlanList.get(position).getDesc());
        String amt=String.valueOf(mRechargePlanList.get(position).getRs());
        holder.planPrice.setText("\u20B9"+amt);
        holder.validity.setText(mRechargePlanList.get(position).getValidity());
        holder.lastUpdate.setText(mRechargePlanList.get(position).getLastUpdate());
        holder.mItemView.setTag(position);
        holder.chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = (int) v.getTag();
                mOnClickHandler.onClick(mRechargePlanList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRechargePlanList.size();
    }

    static class PlanHolder extends RecyclerView.ViewHolder{

        AppCompatTextView desc, validity,lastUpdate, planPrice;
        AppCompatButton chooseButton;
        View mItemView;
        PlanHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            desc = itemView.findViewById(R.id.desc);
            validity = itemView.findViewById(R.id.validity);
            lastUpdate = itemView.findViewById(R.id.last_update);
            planPrice = itemView.findViewById(R.id.top_up_plan_price);
            chooseButton = itemView.findViewById(R.id.select_plan);
        }
    }
}
