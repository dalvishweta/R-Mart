package com.rmart.retiler.inventory.category.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.CategoryItemRowBinding;
import com.rmart.retiler.inventory.brand.model.Brand;
import com.rmart.retiler.inventory.category.listner.OnClickListner;
import com.rmart.retiler.inventory.category.model.Category;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Category> categories;
    Context context;
    OnClickListner onClickListner;
   public CategoryListAdapter(Context context, ArrayList<Category> categories, OnClickListner onClickListner)
   {
       this.categories=categories;
       this.context=context;
       this.onClickListner=onClickListner;

   }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_item_row, parent, false);

        CategoryItemViewHolder vh = new CategoryItemViewHolder(binding); // pass the view to View Holder
        return vh;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       if(holder instanceof  CategoryItemViewHolder) {

           CategoryItemViewHolder brandItemViewHolder = (CategoryItemViewHolder) holder;
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
        CategoryItemRowBinding binding;

        public CategoryItemViewHolder(CategoryItemRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

        }

        public void bind(Object obj) {
              binding.setVariable(BR.category, obj);
              binding.executePendingBindings();
        }
    }
}
