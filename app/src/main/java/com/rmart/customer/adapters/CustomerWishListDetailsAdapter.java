package com.rmart.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.customer.models.ShoppingCartResponseDetails;

import java.util.List;

/**
 * Created by Satya Seshu on 17/09/20.
 */
public class CustomerWishListDetailsAdapter extends RecyclerView.Adapter<CustomerWishListDetailsAdapter.ViewHolder> {

    private List<ShoppingCartResponseDetails> listData;
    private LayoutInflater layoutInflater;
    public CustomerWishListDetailsAdapter(Context context, List<ShoppingCartResponseDetails> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.customer_wish_list_details_items_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        TextView tvDiscountPercentageField;
        TextView tvQuantityField;
        TextView tvSellingPriceField;
        TextView tvTotalPriceField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            tvDiscountPercentageField = itemView.findViewById(R.id.tv_discount_percent_field);
            tvQuantityField = itemView.findViewById(R.id.tv_quantity_field);
            tvSellingPriceField = itemView.findViewById(R.id.tv_selling_price_field);
            tvTotalPriceField = itemView.findViewById(R.id.tv_total_price_field);
        }
    }
}
