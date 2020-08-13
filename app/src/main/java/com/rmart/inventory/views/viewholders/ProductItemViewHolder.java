package com.rmart.inventory.views.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;

public class ProductItemViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView productName, quantity, units, price;
    public ProductItemViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.product_name);
        quantity = itemView.findViewById(R.id.quantity_value);
        units = itemView.findViewById(R.id.unit_value);
        price = itemView.findViewById(R.id.cost_value);
    }
}
