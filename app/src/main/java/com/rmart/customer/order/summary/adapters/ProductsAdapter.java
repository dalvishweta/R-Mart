package com.rmart.customer.order.summary.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.models.CustomerOrderProductOrderedDetails;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.databinding.LoadMoreItemBinding;
import com.rmart.databinding.ProductItemViewBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CustomerOrderProductOrderedDetails> productDatas;
    Activity context;
    public ProductsAdapter(Activity context, ArrayList<CustomerOrderProductOrderedDetails> productDatas) {
        this.context = context;
        this.productDatas =(productDatas);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductItemViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_item_view, parent, false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductHolder productHolder=  (ProductHolder)  holder;
        productHolder.bind(productDatas.get(position));

    }

    @Override
    public int getItemCount() {
        return productDatas!=null?productDatas.size():0;
    }


    public class ProductHolder extends RecyclerView.ViewHolder {

        ProductItemViewBinding binding;

        public ProductHolder(ProductItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.CustomerOrderProductOrderedDetails, obj);
            binding.executePendingBindings();
        }
    }
}
