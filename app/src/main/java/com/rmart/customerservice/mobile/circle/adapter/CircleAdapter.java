package com.rmart.customerservice.mobile.circle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customerservice.mobile.circle.model.Circle;
import com.rmart.customerservice.mobile.listners.SlectCircle;
import com.rmart.databinding.CircleItemRowBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.OperatoreViewHolder> {
    Context context;
    SlectCircle slectCircle;
    ArrayList<Circle> circles = new ArrayList<>();
    public CircleAdapter(Context context, ArrayList<Circle> circles, SlectCircle slectCircle) {
        this.circles=circles;
        this.context=context;
        this.slectCircle=slectCircle;
    }
    @Override
    public OperatoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CircleItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.circle_item_row, parent, false);
        OperatoreViewHolder vh = new OperatoreViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OperatoreViewHolder holder, int position) {

        holder.bind(circles.get(position));
        holder.contactListItemBinding.getRoot().setOnClickListener(view -> slectCircle.onSelect(circles.get(position)));
    }

    @Override
    public int getItemCount() {
        return circles==null?0:circles.size();
    }


    class OperatoreViewHolder extends RecyclerView.ViewHolder {
       public CircleItemRowBinding contactListItemBinding ;
        OperatoreViewHolder(CircleItemRowBinding contactListItemBinding) {
            super(contactListItemBinding.getRoot());
            this.contactListItemBinding=contactListItemBinding;
        }
        public void bind(Object obj) {
            contactListItemBinding.setVariable(BR.Circles, obj);
            contactListItemBinding.executePendingBindings();
        }
    }
}