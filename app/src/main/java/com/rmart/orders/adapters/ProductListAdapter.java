package com.rmart.orders.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.inventory.views.viewholders.ProductItemViewHolder;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.pojos.orders.Product;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductItemViewHolder> {

    View.OnClickListener onClickListener;
    ArrayList<Product> productList;
    private ImageLoader imageLoader;
    public ProductListAdapter(ArrayList<Product> orderList, View.OnClickListener onClickListener) {
        this.productList = orderList;
        this.onClickListener =onClickListener;
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }
    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_order_product_item, parent, false);
        if(null != onClickListener) {
            listItem.setOnClickListener(onClickListener);
        }
        return new ProductItemViewHolder(listItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        Product productObject = productList.get(position);
        holder.price.setText(productObject.getPrice());
        holder.productName.setText(productObject.getProductName());
        holder.quantity.setText(productObject.getQuantity()+"");
        String unitsDetails = String.format("%s %s", productObject.getUnit(), productObject.getUnitMeasure());
        holder.units.setText(unitsDetails);
        String imageUrl = productObject.getDisplayImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imageView,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            holder.imageView.setImageUrl(imageUrl, RMartApplication.getInstance().getImageLoader());
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
