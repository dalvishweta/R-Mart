package com.rmart.customerservice.mobile.operators.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customerservice.mobile.listners.OnContactSelectContactListner;
import com.rmart.customerservice.mobile.listners.SlectOperator;
import com.rmart.customerservice.mobile.operators.model.Operator;
import com.rmart.databinding.OperatorRowBinding;
import com.rmart.databinding.OperatoresItemRowBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.OperatoreViewHolder> {
    Context context;
    SlectOperator slectOperator;
    ArrayList<Operator> operators = new ArrayList<>();
    public OperatorAdapter(Context context,ArrayList<Operator> operators, SlectOperator slectOperator) {
        this.operators=operators;
        this.context=context;
        this.slectOperator=slectOperator;
    }
    @Override
    public OperatoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OperatorRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.operator_row, parent, false);
        OperatoreViewHolder vh = new OperatoreViewHolder(binding);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull OperatoreViewHolder holder, int position) {

        holder.bind(operators.get(position));
        holder.contactListItemBinding.getRoot().setOnClickListener(view -> {
            try{
                if(slectOperator!=null) {
                    slectOperator.onSelect(operators.get(position));
                }
            }
            catch (Exception e){

            }


        });
    }
    @Override
    public int getItemCount() {
        return operators.size();
    }
    static class OperatoreViewHolder extends RecyclerView.ViewHolder {
       public OperatorRowBinding contactListItemBinding ;
        OperatoreViewHolder(OperatorRowBinding contactListItemBinding) {
            super(contactListItemBinding.getRoot());
            this.contactListItemBinding=contactListItemBinding;
        }
        public void bind(Object obj) {
            contactListItemBinding.setVariable(BR.Operator, obj);
            contactListItemBinding.executePendingBindings();
        }
    }
}