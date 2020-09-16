package com.rmart.customer.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.views.ProgressBarCircular;
import com.rmart.customer.models.CustomerProductsModel;
import com.rmart.utilits.HttpsTrustManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class CustomerProductsListAdapter extends RecyclerView.Adapter<CustomerProductsListAdapter.ViewHolder> implements Filterable {

    private List<CustomerProductsModel> productList;
    private LayoutInflater layoutInflater;
    private List<CustomerProductsModel> filteredListData;
    private MyFilter myFilter;
    private ImageLoader imageLoader;
    private CallBackInterface callBackListener;

    public CustomerProductsListAdapter(Context context, List<CustomerProductsModel> productList, CallBackInterface callBackListener) {
        this.productList = productList;
        layoutInflater = LayoutInflater.from(context);
        filteredListData = new ArrayList<>();
        this.filteredListData.addAll(productList);
        imageLoader = RMartApplication.getInstance().getImageLoader();
        this.callBackListener = callBackListener;
    }

    public void updateItems(List<CustomerProductsModel> listData) {
        this.productList = listData;
        this.filteredListData.clear();
        this.filteredListData.addAll(productList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.customer_products_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerProductsModel customerProductsModel = productList.get(position);
        holder.tvShopNameField.setText(customerProductsModel.getShopName());
        holder.tvPhoneNoField.setText(customerProductsModel.getShopMobileNo());
        holder.tvViewAddressField.setText(customerProductsModel.getShopAddress());

        String shopImageUrl = customerProductsModel.getShopImage();
        if(!TextUtils.isEmpty(shopImageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(shopImageUrl, ImageLoader.getImageListener(holder.ivShopImageField,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            holder.ivShopImageField.setImageUrl(shopImageUrl, imageLoader);
        }

        /*holder.ivShopImageField.setTag(position);
        holder.ivShopImageField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            CustomerProductsModel selectedDetails = productList.get(tag);
            callBackListener.callBackReceived(selectedDetails);
        });*/
        holder.ivMessageField.setTag(position);
        holder.ivCallIconField.setTag(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return filteredListData.size();
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView ivShopImageField;
        TextView tvShopNameField;
        TextView tvPhoneNoField;
        TextView tvViewAddressField;
        ImageView ivFavouriteField;
        ImageView ivCallIconField;
        ImageView ivMessageField;
        ProgressBarCircular progressBarCircular;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShopImageField = itemView.findViewById(R.id.iv_shop_image);
            tvShopNameField = itemView.findViewById(R.id.tv_shop_name_field);
            tvPhoneNoField = itemView.findViewById(R.id.tv_phone_no_field);
            tvViewAddressField = itemView.findViewById(R.id.tv_view_address_field);
            ivFavouriteField = itemView.findViewById(R.id.iv_favourite_image);
            ivCallIconField = itemView.findViewById(R.id.iv_call_field);
            ivMessageField = itemView.findViewById(R.id.iv_message_field);

            progressBarCircular = itemView.findViewById(R.id.profile_circular_field);
            itemView.setOnClickListener(v -> {
                int tag = (int) itemView.getTag();
                CustomerProductsModel selectedDetails = filteredListData.get(tag);
                callBackListener.callBackReceived(selectedDetails);
            });
        }
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                filteredListData.clear();
                for (CustomerProductsModel customerProductsModel : productList) {
                    if (customerProductsModel.getShopName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListData.add(customerProductsModel);
                    }
                }
            }
            results.count = filteredListData.size();
            results.values = filteredListData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData = (List<CustomerProductsModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
