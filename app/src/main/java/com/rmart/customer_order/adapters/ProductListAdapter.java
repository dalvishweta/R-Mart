package com.rmart.customer_order.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.inventory.views.viewholders.ProductItemViewHolder;
import com.rmart.utilits.pojos.orders.Product;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductItemViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<Product> productList;
    public ProductListAdapter(ArrayList<Product> orderList, View.OnClickListener onClickListener) {
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
        Product productObject = productList.get(position);
        holder.price.setText(productObject.getPrice());
        holder.productName.setText(productObject.getProductName());
        holder.quantity.setText(productObject.getQuantity());
        holder.units.setText(productObject.getUnit()+" " +productObject.getUnitMeasure());
        if(productObject.getDisplayImage().length() > 10) {
            ImageLoader imageLoader = RMartApplication.getInstance().getImageLoader();
            holder.imageView.setImageUrl("http://it.rokad.in/uploads/product_image/default_product.png", imageLoader);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
