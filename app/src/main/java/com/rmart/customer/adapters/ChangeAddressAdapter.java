package com.rmart.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customer.models.CustomerProductsModel;

import java.util.List;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class ChangeAddressAdapter extends RecyclerView.Adapter<ChangeAddressAdapter.ViewHolder> {

    private List<Object> productList;
    private LayoutInflater layoutInflater;

    public ChangeAddressAdapter(Context context, List<Object> productList) {
        this.productList = productList;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateItems(List<Object> listData) {
        this.productList = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.change_address_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //CustomerProductsModel customerProductsModel = productList.get(position);
        //holder.tvAddressField.setText(customerProductsModel.getShopMobileNo());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ivAddressCheckableField;
        TextView tvAddressField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAddressCheckableField = itemView.findViewById(R.id.iv_address_checkable_field);
            tvAddressField = itemView.findViewById(R.id.tv_address_field);
        }
    }
}
