package com.rmart.inventory.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.views.viewholders.CategoryViewHolder;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    boolean isGridView;
    View.OnClickListener onClickListener;
    public CategoryAdapter(boolean _listType, View.OnClickListener onClickListener) {
        isGridView = _listType;
        this.onClickListener =onClickListener;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        if (isGridView) {
            listItem= layoutInflater.inflate(R.layout.item_grid_layout, parent, false);
        } else {
            listItem= layoutInflater.inflate(R.layout.item_list_layout, parent, false);
        }
        listItem.setOnClickListener(onClickListener);
        return new CategoryViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
