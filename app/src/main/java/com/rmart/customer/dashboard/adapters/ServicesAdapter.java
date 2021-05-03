package com.rmart.customer.dashboard.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.dashboard.model.ServiceOffer;
import com.rmart.customer.dashboard.listners.OnClick;
import com.rmart.databinding.ServicesRowItemBinding;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int HOMEPAGECATEGORY=222;

    public ArrayList<ServiceOffer> serviceOffers = new ArrayList<>();
    Activity context;
    OnClick onClick;

    public ServicesAdapter(Activity context, ArrayList<ServiceOffer> serviceOffers,OnClick onClick) {
        this.context = context;
        this.serviceOffers = serviceOffers;
        this.onClick = onClick;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ServicesRowItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.services_row_item, parent, false);
        ServicesViewHolder vh = new ServicesViewHolder(binding); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {

        ServicesViewHolder myViewHolder=     (ServicesViewHolder ) holder2;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        myViewHolder.binding.root.getLayoutParams().width = (width / 4) -28 ;
        myViewHolder.binding.root.getLayoutParams().height = (width / 4);
        myViewHolder.binding.imgDesignlistlayout.getLayoutParams().height = (width / 4)- 120;
        myViewHolder.bind(serviceOffers.get(position));
        myViewHolder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onServiceClick(serviceOffers.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {
        return serviceOffers.size();
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder {

        ServicesRowItemBinding binding;

        public ServicesViewHolder(ServicesRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.ServiceOffer, obj);
            binding.executePendingBindings();
        }
    }

}