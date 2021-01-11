package com.rmart.retiler.inventory.brand.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.BrandItemRowBinding;
import com.rmart.retiler.inventory.brand.listner.OnClickListner;
import com.rmart.retiler.inventory.brand.model.Brand;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BrandListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Brand> brands;
    Context context;
    OnClickListner onClickListner;
   public   BrandListAdapter(Context context,ArrayList<Brand> brands,OnClickListner onClickListner)
   {
       this.brands=brands;
       this.context=context;
       this.onClickListner=onClickListner;

   }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BrandItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.brand_item_row, parent, false);

        BrandItemViewHolder vh = new BrandItemViewHolder(binding); // pass the view to View Holder
        return vh;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       if(holder instanceof  BrandItemViewHolder) {

           BrandItemViewHolder brandItemViewHolder = (BrandItemViewHolder) holder;
            final Brand brand =brands.get(position);
           brandItemViewHolder.bind(brand);
           brandItemViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(onClickListner!=null) {
                       onClickListner.onClick(brand);
                   }
               }
           });


       }

    }

    @Override
    public int getItemCount() {
        return brands!=null?brands.size():0;
    }


    public class BrandItemViewHolder extends RecyclerView.ViewHolder {
        BrandItemRowBinding binding;

        public BrandItemViewHolder(BrandItemRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

        }

        public void bind(Object obj) {
              binding.setVariable(BR.brand, obj);
              binding.executePendingBindings();
        }
    }
}
