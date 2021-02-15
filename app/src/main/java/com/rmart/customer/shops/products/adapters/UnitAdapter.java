package com.rmart.customer.shops.products.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.shops.home.adapters.ProductsAdapter;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.listner.OnUnitSelect;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.databinding.LoadMoreItemBinding;
import com.rmart.databinding.UnitRowItemBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UnitAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    ArrayList<CustomerProductsDetailsUnitModel> customerProductsDetailsUnitModels;

    public void setSelectedCustomerProductsDetailsUnitModel(CustomerProductsDetailsUnitModel selectedCustomerProductsDetailsUnitModel) {
        this.selectedCustomerProductsDetailsUnitModel = selectedCustomerProductsDetailsUnitModel;
        notifyDataSetChanged();
    }

    CustomerProductsDetailsUnitModel selectedCustomerProductsDetailsUnitModel;
    OnUnitSelect onClickListner;


    public UnitAdapter(Activity context, ArrayList<CustomerProductsDetailsUnitModel> customerProductsDetailsUnitModels, OnUnitSelect onClickListner) {
        this.context = context;
        this.customerProductsDetailsUnitModels = customerProductsDetailsUnitModels;
        this.onClickListner = onClickListner;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UnitRowItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.unit_row_item, parent, false);
        UnitRowHolder vh = new UnitRowHolder(binding); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CustomerProductsDetailsUnitModel unitModel = customerProductsDetailsUnitModels.get(position);
        UnitRowHolder unitRowHolder   = (UnitRowHolder) holder;
        if(selectedCustomerProductsDetailsUnitModel!=null && selectedCustomerProductsDetailsUnitModel.getProductUnitId() == unitModel.getProductUnitId()){
            unitModel.selected = false;
        } else {
            unitModel.selected = true;
        }
        unitRowHolder.bind(unitModel);
        unitRowHolder.binding.getRoot().setOnClickListener(view -> {
            onClickListner.onSelect(unitModel);
        });
    }

    @Override
    public int getItemCount() {
        return customerProductsDetailsUnitModels==null?0:customerProductsDetailsUnitModels.size();
    }
    public class UnitRowHolder extends RecyclerView.ViewHolder {

        UnitRowItemBinding binding;

        public UnitRowHolder(UnitRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.customerProductsDetailsUnitModel, obj);
            binding.executePendingBindings();
        }
    }
}
