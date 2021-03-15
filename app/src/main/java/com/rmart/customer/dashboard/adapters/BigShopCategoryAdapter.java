package com.rmart.customer.dashboard.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.dashboard.listners.OnClick;
import com.rmart.customer.dashboard.model.BigShopType;
import com.rmart.customer.dashboard.model.ShopType;
import com.rmart.databinding.BigShowTypeRowItemBinding;
import com.rmart.databinding.ShopCategoryItemRowBinding;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BigShopCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int HOMEPAGECATEGORY=222;

    public ArrayList<BigShopType> categories = new ArrayList<>();
    Activity context;
    OnClick onClickListner;
    public BigShopCategoryAdapter(Activity context, ArrayList<BigShopType> categories,OnClick onClickListner) {
        this.context = context;
        this.categories = categories;
        this.onClickListner = onClickListner;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            BigShowTypeRowItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.big_show_type_row_item, parent, false);
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
        myViewHolder.binding.topview.getLayoutParams().width = (width / 2) - 52;
        myViewHolder.binding.topview.getLayoutParams().height = (width / 2)-(width / 2)/3;
        myViewHolder.bind(categories.get(position));
        myViewHolder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListner.onBigShopClick(categories.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {


        return categories.size();


    }

    public class ShopCategoryHolder extends RecyclerView.ViewHolder {

        BigShowTypeRowItemBinding binding;

        public ShopCategoryHolder(BigShowTypeRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.BigShopType, obj);
            binding.executePendingBindings();
        }
    }

}