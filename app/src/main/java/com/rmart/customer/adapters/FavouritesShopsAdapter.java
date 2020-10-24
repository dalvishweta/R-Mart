package com.rmart.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.customer.models.ShopWiseWishListResponseDetails;

import java.util.List;

/**
 * Created by Satya Seshu on 24/10/20.
 */
public class FavouritesShopsAdapter extends RecyclerView.Adapter<FavouritesShopsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<ShopWiseWishListResponseDetails> listData;
    private final CallBackInterface callBackListener;

    public FavouritesShopsAdapter(Context context, List<ShopWiseWishListResponseDetails> listData, CallBackInterface callBackListener) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.callBackListener = callBackListener;
    }

    public void updateItems(List<ShopWiseWishListResponseDetails> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.favourites_list_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnRemoveField.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView ivShopImageField;
        TextView tvShopNameField;
        TextView tvViewAddressField;
        AppCompatButton btnRemoveField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShopImageField = itemView.findViewById(R.id.iv_shop_image_field);
            tvShopNameField = itemView.findViewById(R.id.tv_shop_name_field);
            tvViewAddressField = itemView.findViewById(R.id.tv_shop_address_field);
            btnRemoveField = itemView.findViewById(R.id.btn_remove_field);

            btnRemoveField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                callBackListener.callBackReceived(tag);
            });
        }
    }
}
