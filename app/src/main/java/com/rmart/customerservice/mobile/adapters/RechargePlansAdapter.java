package com.rmart.customerservice.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customerservice.mobile.interfaces.OnPlanSelectedHandler;
import com.rmart.customerservice.mobile.models.mPlans.RechargePlans;
import com.rmart.utilits.Curruncy;

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


        RechargePlans rechargePlans=mRechargePlanList.get(position);
        holder.desc.setText(rechargePlans.getDesc());
        String amt=String.valueOf(rechargePlans.getRs());

        holder.planPrice.setText(Curruncy.getCurruncy(amt));
        holder.validity.setText(String.format("Validity: %s", mRechargePlanList.get(position).getValidity()));
        holder.mItemView.setTag(position);
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = (int) v.getTag();
                mOnClickHandler.onClick(mRechargePlanList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRechargePlanList!=null?mRechargePlanList.size():0;
    }

    static class PlanHolder extends RecyclerView.ViewHolder{

        AppCompatTextView desc, validity, planPrice;
        View mItemView;
        PlanHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            desc = itemView.findViewById(R.id.desc);
            validity = itemView.findViewById(R.id.validity);
            planPrice = itemView.findViewById(R.id.top_up_plan_price);
        }
    }
}
