package com.rmart.inventory.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.views.viewholders.CategoryViewHolder;

public class ProductAdapter extends RecyclerView.Adapter {

    int listType =0;
    View.OnClickListener onClickListener;
    public ProductAdapter(View.OnClickListener onClickListener) {
        this.onClickListener =onClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem= layoutInflater.inflate(R.layout.grid_product_layout, parent, false);
        listItem.setOnClickListener(onClickListener);
        return new CategoryViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
