package com.rmart.customer.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.models.Product;

import java.util.List;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class CustomerProductsListAdapter extends RecyclerView.Adapter<CustomerProductsListAdapter.ViewHolder> {

    private List<Product> productList;
    private LayoutInflater layoutInflater;

    public CustomerProductsListAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.customer_products_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ivProfileImageField;
        TextView tvShopNameField;
        TextView tvPhoneNoField;
        Button tvViewAddressField;
        ImageView ivFavouriteField;
        ImageView ivCallIconField;
        ImageView ivMessageField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImageField = itemView.findViewById(R.id.iv_profile_image);
            tvShopNameField = itemView.findViewById(R.id.tv_shop_name_field);
            tvPhoneNoField = itemView.findViewById(R.id.tv_phone_no_field);
            tvViewAddressField = itemView.findViewById(R.id.tv_view_address_field);
            ivFavouriteField = itemView.findViewById(R.id.iv_favourite_image);
            ivCallIconField = itemView.findViewById(R.id.iv_call_field);
            ivMessageField = itemView.findViewById(R.id.iv_message_field);
        }
    }
}
