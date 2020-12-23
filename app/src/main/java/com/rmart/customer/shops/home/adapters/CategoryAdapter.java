package com.rmart.customer.shops.home.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.shops.home.fragments.ShopHomePage;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.Results;
import com.rmart.databinding.ShopHomePageBinding;
import com.rmart.databinding.ShopHomePageCategoryItemsBinding;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Integer> resources = new ArrayList<Integer>();
    int resourcescuruntposition = 0;
    OnClickListner onClickListner;
    ArrayList<Category> categories = new ArrayList<>();
    Activity context;


    public CategoryAdapter(Activity context, ArrayList<Category> categories,OnClickListner onClickListner) {
        this.context = context;
        this.onClickListner = onClickListner;

        this.categories.addAll(categories);

        resources.add(R.drawable.cat_background_1);
        resources.add(R.drawable.cat_background_2);
        resources.add(R.drawable.cat_background_3);
        resources.add(R.drawable.cat_background_4);
        resources.add(R.drawable.cat_background_5);
        resources.add(R.drawable.cat_background_6);
        resources.add(R.drawable.cat_background_0);



    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ShopHomePageCategoryItemsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.shop_home_page_category_items, parent, false);
        CategoryHolder vh = new CategoryHolder(binding); // pass the view to View Holder
        return vh;

//
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {

        if(holder2 instanceof CategoryHolder) {
            CategoryHolder myViewHolder=     (CategoryHolder ) holder2;
            myViewHolder.bind(categories.get(position));

            ((CategoryHolder) holder2).binding.root.setBackground(context.getResources().getDrawable(resources.get(resourcescuruntposition)));
            resourcescuruntposition++;
            if(resources.size()<=resourcescuruntposition) {
                resourcescuruntposition=0;
            }
            myViewHolder.binding.topview.setOnClickListener(view -> onClickListner.onCategorySelected(categories.get(position)));
        }


    }


    @Override
    public int getItemCount() {


        return categories.size();


    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        ShopHomePageCategoryItemsBinding binding;

        public CategoryHolder(ShopHomePageCategoryItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.category, obj);
            binding.executePendingBindings();
        }
    }
}