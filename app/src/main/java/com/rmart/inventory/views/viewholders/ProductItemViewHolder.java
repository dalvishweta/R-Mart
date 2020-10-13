package com.rmart.inventory.views.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;

public class ProductItemViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView productName;
    public AppCompatTextView tvProductPriceQuantityDetailsField;
    public NetworkImageView imageView;
    public ProductItemViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.product_name);
        imageView = itemView.findViewById(R.id.product_image);
        tvProductPriceQuantityDetailsField = itemView.findViewById(R.id.tv_product_price_quantity_details_field);
    }
}
