package com.rmart.inventory.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.views.viewholders.SubCategoryViewHolder;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryViewHolder> {

    boolean isGridView;
    View.OnClickListener onClickListener;
    public SubCategoryAdapter(boolean _listType, View.OnClickListener onClickListener) {
        isGridView = _listType;
        this.onClickListener =onClickListener;
    }
    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        if (isGridView) {
            listItem= layoutInflater.inflate(R.layout.item_grid_layout, parent, false);
        } else {
            listItem= layoutInflater.inflate(R.layout.item_list_layout, parent, false);
        }
        listItem.setOnClickListener(onClickListener);
        return new SubCategoryViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
