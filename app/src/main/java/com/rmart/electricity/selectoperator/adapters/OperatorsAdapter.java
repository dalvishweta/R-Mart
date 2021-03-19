package com.rmart.electricity.selectoperator.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.OperatoresItemRowBinding;

import com.rmart.electricity.selectoperator.listner.OperatorListner;
import com.rmart.electricity.selectoperator.model.Operator;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class OperatorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    public ArrayList<Operator> allOperators = new ArrayList<>();
    public ArrayList<Operator> operators = new ArrayList<>();
    Activity context;
    OperatorListner onClick;

    public OperatorsAdapter(Activity context, ArrayList<Operator> operators, OperatorListner onClick) {
        this.context = context;
        this.operators = operators;
        this.allOperators = operators;
        this.onClick = onClick;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        OperatoresItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.operatores_item_row, parent, false);
        OperatorsViewHolder vh = new OperatorsViewHolder(binding); // pass the view to View Holder
        return vh;


//
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {

        OperatorsViewHolder myViewHolder=     (OperatorsViewHolder) holder2;

        myViewHolder.bind(operators.get(position));
        if(position==0) {
            myViewHolder.binding.label.setVisibility(View.VISIBLE);

        } else {
            myViewHolder.binding.label.setVisibility(View.GONE);
        }
        myViewHolder.binding.icon.setImageDrawable(context.getResources().getDrawable(operators.get(position).icon));
        myViewHolder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onClick.OnSelect(operators.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {


        return operators.size();


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    operators = allOperators;
                } else {
                    List<Operator> filteredList = new ArrayList<>();
                    for (Operator row : allOperators) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    operators = (ArrayList<Operator>) filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = operators;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                operators = (ArrayList<Operator>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class OperatorsViewHolder extends RecyclerView.ViewHolder {

        OperatoresItemRowBinding binding;

        public OperatorsViewHolder(OperatoresItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.operator, obj);
            binding.executePendingBindings();
        }
    }

}