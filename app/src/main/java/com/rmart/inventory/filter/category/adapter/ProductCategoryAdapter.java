package com.rmart.inventory.filter.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.CategoryFilterLayoutBinding;
import com.rmart.databinding.CategoryItemRowBinding;
import com.rmart.inventory.filter.category.listner.OnClickListner;
import java.util.ArrayList;
import com.rmart.retiler.inventory.category.model.Category;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Category> categories;
    Context context;
    OnClickListner onClickListner;
    public ProductCategoryAdapter(Context context, ArrayList<Category> categories, OnClickListner onClickListner)
    {
        this.categories=categories;
        this.context=context;
        this.onClickListner=onClickListner;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryFilterLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_filter_layout, parent, false);

        ProductCategoryAdapter.CategoryItemViewHolder vh = new ProductCategoryAdapter.CategoryItemViewHolder(binding); // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ProductCategoryAdapter.CategoryItemViewHolder) {

            ProductCategoryAdapter.CategoryItemViewHolder brandItemViewHolder = (ProductCategoryAdapter.CategoryItemViewHolder) holder;
            final Category category =categories.get(position);
            brandItemViewHolder.bind(category);
            brandItemViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickListner!=null) {
                        onClickListner.onClick(category);
                    }
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return categories!=null?categories.size():0;
    }


    public class CategoryItemViewHolder extends RecyclerView.ViewHolder {
        CategoryFilterLayoutBinding binding;

        public CategoryItemViewHolder(CategoryFilterLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.category, obj);
            binding.executePendingBindings();
        }
    }
}

