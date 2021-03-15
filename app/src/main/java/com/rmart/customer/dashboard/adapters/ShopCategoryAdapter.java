package com.rmart.customer.dashboard.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.dashboard.listners.OnClick;
import com.rmart.customer.dashboard.model.ShopType;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.databinding.CategoryListItemRowBinding;
import com.rmart.databinding.ShopCategoryItemRowBinding;
import com.rmart.databinding.ShopHomePageCategoryItemsBinding;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ShopCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int HOMEPAGECATEGORY=222;

    public ArrayList<ShopType> categories = new ArrayList<>();
    OnClick onClickListner;
    Activity context;

    public ShopCategoryAdapter(Activity context, ArrayList<ShopType> categories,OnClick onClickListner) {
        this.context = context;
        this.categories = categories;
        this.onClickListner = onClickListner;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ShopCategoryItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.shop_category_item_row, parent, false);
            ShopCategoryHolder vh = new ShopCategoryHolder(binding); // pass the view to View Holder
            return vh;


//
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {

        ShopCategoryHolder myViewHolder=     (ShopCategoryHolder ) holder2;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        myViewHolder.binding.topview.getLayoutParams().width = (width / 4) - 15;
        myViewHolder.binding.topview.getLayoutParams().height = (width / 4) ;
        myViewHolder.binding.imgDesignlistlayout.getLayoutParams().height = (width / 4)- 100;
        myViewHolder.bind(categories.get(position));
        myViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListner.onShopClick(categories.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {


        return categories.size();


    }

    public class ShopCategoryHolder extends RecyclerView.ViewHolder {

        ShopCategoryItemRowBinding binding;

        public ShopCategoryHolder(ShopCategoryItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.ShopType, obj);
            binding.executePendingBindings();
        }
    }

}