package com.rmart.customerservice.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customerservice.mobile.interfaces.RecyclerOnClickHandler;
import com.rmart.customerservice.mobile.models.SubscriberModule;

import java.util.ArrayList;

public class SubscriberListAdapter extends RecyclerView.Adapter<SubscriberListAdapter.ListViewHolder> implements View.OnClickListener {

    //ArrayList<Integer> subscriberImgs;
    ArrayList<SubscriberModule> subscriberModules;
    public RecyclerOnClickHandler mRecyclerOnClickHandler;
    private Context mContext;
    private int selected;
    private View selectedView;

    public SubscriberListAdapter(RecyclerOnClickHandler mRecyclerOnClickHandler,
                                 Context mContext, ArrayList<SubscriberModule> _subscriberModules) {
        this.mRecyclerOnClickHandler = mRecyclerOnClickHandler;
        this.mContext = mContext;
        subscriberModules = _subscriberModules;
    }



    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscriber_list_item,parent,false);
        return new ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.subscriberImg.setImageResource(subscriberModules.get(position).getImage());
        holder.subscriberImg.setTag(position);
        holder.subscriberImg.setOnClickListener(this);
        if(subscriberModules.get(position).isSelected()) {
            holder.subscriberImg.setAlpha(1f);
            if(selected != position || null == selectedView) {
                selectedView = holder.subscriberImg;
                selected = position;
            }
        } else {
            holder.subscriberImg.setAlpha(.5f);
        }
    }

    @Override
    public int getItemCount() {
        return subscriberModules.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if(null != selectedView) {
            selectedView.setAlpha(.5f);
        }
        selectedView = v;
        if(selected != position) {
            subscriberModules.get(selected).setSelected(false);
        }
        mRecyclerOnClickHandler.onClick(position,v);
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView subscriberImg;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            subscriberImg = itemView.findViewById(R.id.subscriber_img);
        }


    }
}
