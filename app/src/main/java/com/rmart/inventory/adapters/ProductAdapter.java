package com.rmart.inventory.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.models.Product;
import com.rmart.inventory.views.viewholders.ProductViewHolder;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<Product> productList;
    public ProductAdapter(ArrayList<Product> productList, View.OnClickListener onClickListener) {
        this.onClickListener =onClickListener;
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem= layoutInflater.inflate(R.layout.grid_product_layout, parent, false);
        listItem.setOnClickListener(onClickListener);
        return new ProductViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.itemView.setTag(product);
        String html =  "<strike>" + product.getUnitObjects().get(0).getActualCost()+"</strike>";
        holder.tvActual.setText(Html.fromHtml(html));
        // holder.tvActual.setText(Html.fromHtml());
        holder.tvFinalCost.setText(product.getUnitObjects().get(0).getFinalCost());
        holder.tvItemTitle.setText(product.getName());
        String text = holder.itemView.getContext().getString(R.string.offer);
        String temp =String.format(text, product.getUnitObjects().get(0).getDiscount());
        holder.tvOffer.setText(temp);
        if(product.getUnitObjects().size()>1) {
            holder.availableUnits. setVisibility(View.VISIBLE);
            holder.availableUnits. setText(String.format(holder.itemView.getContext().getString(R.string.available_other_sizes), product.getUnitObjects().size()+""));
        } else {
            holder.availableUnits. setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
