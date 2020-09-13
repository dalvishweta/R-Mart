package com.rmart.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;

import java.util.ArrayList;

/**
 * Created by Satya Seshu on 12/09/20.
 */
public class ConfirmOrdersAdapter extends RecyclerView.Adapter<ConfirmOrdersAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Object> listData;

    public ConfirmOrdersAdapter(Context context, ArrayList<Object> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.confirm_order_list_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.btnMoveToWishListField.setTag(position);
        holder.btnMoveToWishListField.setOnClickListener(v -> {

        });
        holder.deleteProductField.setTag(position);
        holder.deleteProductField.setOnClickListener(v -> {

        });
        holder.btnMinusField.setTag(position);
        holder.btnMinusField.setOnClickListener(v -> {

        });
        holder.btnPlusField.setTag(position);
        holder.btnPlusField.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        Button btnMoveToWishListField;
        Button deleteProductField;
        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        Button btnMinusField;
        Button btnPlusField;
        TextView tvNoOfQuantityField;
        TextView tvCurrentPriceField;
        TextView tvTotalPriceField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrentPriceField = itemView.findViewById(R.id.tv_current_price_field);
            tvTotalPriceField = itemView.findViewById(R.id.tv_total_price_field);
            btnMinusField = itemView.findViewById(R.id.btn_minus_field);
            tvNoOfQuantityField = itemView.findViewById(R.id.tv_no_of_quantity_field);
            btnPlusField = itemView.findViewById(R.id.btn_add_field);
        }
    }
}
