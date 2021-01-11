package com.rmart.retiler.inventory.product.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.BrandItemRowBinding;
import com.rmart.databinding.ProductItemRowBinding;
import com.rmart.retiler.inventory.product.model.Product;

import java.util.ArrayList;

public class ProductSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Product> products;
    Context context;

   public ProductSearchListAdapter(Context context, ArrayList<Product> products)
   {
       this.products=products;
       this.context=context;

   }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_item_row, parent, false);

        ProductItemViewHolder vh = new ProductItemViewHolder(binding); // pass the view to View Holder
        return vh;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       if(holder instanceof  ProductItemViewHolder) {

           ProductItemViewHolder productItemViewHolder = (ProductItemViewHolder) holder;
           Product brand =products.get(position);
           productItemViewHolder.bind(brand);


       }

    }

    @Override
    public int getItemCount() {
        return products!=null?products.size():0;
    }


    public class ProductItemViewHolder extends RecyclerView.ViewHolder {

        ProductItemRowBinding binding;

        public ProductItemViewHolder(ProductItemRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

        }

        public void bind(Object obj) {
              binding.setVariable(BR.product, obj);
              binding.executePendingBindings();
        }
    }
}
