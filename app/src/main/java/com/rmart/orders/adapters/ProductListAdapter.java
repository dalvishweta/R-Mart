package com.rmart.orders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.views.viewholders.ProductItemViewHolder;
import com.rmart.orders.models.ProductObject;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductItemViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<ProductObject> productList;
    public ProductListAdapter(ArrayList<ProductObject> orderList, View.OnClickListener onClickListener) {
        this.productList = orderList;
        this.onClickListener =onClickListener;
    }
    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_order_product_item, parent, false);
        listItem.setOnClickListener(onClickListener);
        return new ProductItemViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        ProductObject productObject = productList.get(position);
        holder.price.setText(productObject.getPrice());
        holder.productName.setText(productObject.getProductName());
        holder.quantity.setText(productObject.getQuantity());
        holder.units.setText(productObject.getUnits());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
