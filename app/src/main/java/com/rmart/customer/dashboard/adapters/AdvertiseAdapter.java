package com.rmart.customer.dashboard.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.dashboard.model.Offer;
import com.rmart.customer.dashboard.model.ShopType;
import com.rmart.databinding.OfferItemRowBinding;
import com.rmart.databinding.ShopCategoryItemRowBinding;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AdvertiseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int HOMEPAGECATEGORY=222;

    public ArrayList<Offer> categories = new ArrayList<>();
    Activity context;

    public AdvertiseAdapter(Activity context, ArrayList<Offer> categories) {
        this.context = context;
        this.categories = categories;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            OfferItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.offer_item_row, parent, false);
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
        myViewHolder.binding.topview.getLayoutParams().height = (width / 4) - 15;
        myViewHolder.bind(categories.get(position));


    }


    @Override
    public int getItemCount() {


        return categories.size();


    }

    public class ShopCategoryHolder extends RecyclerView.ViewHolder {

        OfferItemRowBinding binding;

        public ShopCategoryHolder(OfferItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.Offer, obj);
            binding.executePendingBindings();
        }
    }

}