package com.rmart.customer.shops.home.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.AdvertiseImages;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.home.model.Results;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.databinding.AddItemRowBinding;
import com.rmart.databinding.SearchShopProductRowItemDeatilsBinding;
import com.rmart.databinding.ShopDetailsPageBinding;
import com.rmart.databinding.ShopHomePageBinding;
import com.rmart.databinding.SliderBinding;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class AdverTiseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<AdvertiseImages> results;

    public AdverTiseAdapter(Activity context, ArrayList<AdvertiseImages> results) {
        this.context = context;
        this.results=results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            AddItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.add_item_row, parent, false);
            AddViewHolder vh = new AddViewHolder(binding); // pass the view to View Holder
            return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {
      ;
        AddViewHolder addViewHolder = (AddViewHolder) holder2;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        addViewHolder.binding.topview.getLayoutParams().width = (width / 2) - 15;
        addViewHolder.binding.topview.getLayoutParams().height = (width / 2) - 15;

        addViewHolder.bind(  results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class AddViewHolder extends RecyclerView.ViewHolder {

        AddItemRowBinding binding;

        public AddViewHolder(AddItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.Add, obj);
            binding.executePendingBindings();
        }
    }
}
