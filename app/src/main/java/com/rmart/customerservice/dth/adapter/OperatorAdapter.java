package com.rmart.customerservice.dth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customerservice.mobile.listners.SlectOperator;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.databinding.OperatorRowDthBinding;

import java.util.ArrayList;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.OperatoreViewHolder> {
    Context context;
    SlectOperator slectOperator;
    ArrayList<Operator> operators = new ArrayList<>();
    public OperatorAdapter(Context context, ArrayList<Operator> operators, SlectOperator slectOperator) {
        this.operators=operators;
        this.context=context;
        this.slectOperator=slectOperator;
    }
    @Override
    public OperatoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OperatorRowDthBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.operator_row_dth, parent, false);
        OperatoreViewHolder vh = new OperatoreViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OperatoreViewHolder holder, int position) {
        OperatorAdapter.OperatoreViewHolder myViewHolder= (OperatorAdapter.OperatoreViewHolder) holder;

        myViewHolder.bind(operators.get(position));
        if(position==operators.size()-1) {
            myViewHolder.contactListItemBinding.view.setVisibility(View.GONE);

        } else {
            myViewHolder.contactListItemBinding.view.setVisibility(View.VISIBLE);
        }
        holder.bind(operators.get(position));
        holder.contactListItemBinding.getRoot().setOnClickListener(view -> slectOperator.onSelect(operators.get(position)));
    }

    @Override
    public int getItemCount() {
        return operators.size();
    }


    class OperatoreViewHolder extends RecyclerView.ViewHolder {
       public OperatorRowDthBinding contactListItemBinding ;
        OperatoreViewHolder(OperatorRowDthBinding contactListItemBinding) {
            super(contactListItemBinding.getRoot());
            this.contactListItemBinding=contactListItemBinding;
        }
        public void bind(Object obj) {
            contactListItemBinding.setVariable(BR.Operator, obj);
            contactListItemBinding.executePendingBindings();
        }
    }
}