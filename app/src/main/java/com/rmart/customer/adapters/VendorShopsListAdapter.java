package com.rmart.customer.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsShopDetailsModel;
import com.rmart.utilits.custom_views.CustomNetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class VendorShopsListAdapter extends RecyclerView.Adapter<VendorShopsListAdapter.ViewHolder> implements Filterable {

    private List<CustomerProductsShopDetailsModel> productList;
    private final LayoutInflater layoutInflater;
    private List<CustomerProductsShopDetailsModel> filteredListData;
    private MyFilter myFilter;
    private final ImageLoader imageLoader;
    private final CallBackInterface callBackListener;

    public VendorShopsListAdapter(Context context, List<CustomerProductsShopDetailsModel> productList, CallBackInterface callBackListener) {
        this.productList = productList;
        layoutInflater = LayoutInflater.from(context);
        filteredListData = new ArrayList<>();
        this.filteredListData.addAll(productList);
        imageLoader = RMartApplication.getInstance().getImageLoader();
        this.callBackListener = callBackListener;
    }

    public void updateItems(List<CustomerProductsShopDetailsModel> listData) {
        this.productList = listData;
        this.filteredListData.clear();
        this.filteredListData.addAll(productList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.vendor_shops_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerProductsShopDetailsModel shopDetails = productList.get(position);
        holder.tvShopNameField.setText(shopDetails.getShopName());
        holder.tvPhoneNoField.setText(shopDetails.getShopMobileNo());
        holder.tvViewAddressField.setText(shopDetails.getShopAddress());

        Object lShopImageObject = shopDetails.getShopImage();
        holder.ivShopImageField.setVisibility(View.GONE);
        holder.progressBarLayout.setVisibility(View.VISIBLE);
        if (lShopImageObject instanceof String) {
            String shopImageUrl = (String) lShopImageObject;
            if (!TextUtils.isEmpty(shopImageUrl)) {
                imageLoader.get(shopImageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            holder.ivShopImageField.setLocalImageBitmap(bitmap);
                        }
                        holder.ivShopImageField.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        holder.progressBarLayout.setVisibility(View.GONE);
                        holder.ivShopImageField.setVisibility(View.VISIBLE);
                        holder.ivShopImageField.setBackgroundResource(android.R.drawable.ic_dialog_alert);
                    }
                });
            } else {
                holder.progressBarLayout.setVisibility(View.GONE);
                holder.ivShopImageField.setVisibility(View.VISIBLE);
                holder.ivShopImageField.setBackgroundResource(android.R.drawable.ic_dialog_alert);
            }
        } else {
            holder.progressBarLayout.setVisibility(View.GONE);
            holder.ivShopImageField.setVisibility(View.VISIBLE);
            holder.ivShopImageField.setBackgroundResource(android.R.drawable.ic_dialog_alert);
        }

        boolean isWishListShop = shopDetails.getShopWishListStatus() == 1;
        holder.ivFavouriteImageField.setImageResource(isWishListShop ? R.drawable.heart_active : R.drawable.heart);

        holder.ivShopImageField.setTag(position);
        holder.ivMessageField.setTag(position);
        holder.ivCallIconField.setTag(position);
        holder.ivFavouriteImageField.setTag(position);
        holder.addressLayoutField.setTag(position);
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

        CustomNetworkImageView ivShopImageField;
        TextView tvShopNameField;
        TextView tvPhoneNoField;
        TextView tvViewAddressField;
        ImageView ivFavouriteImageField;
        ImageView ivCallIconField;
        ImageView ivMessageField;
        LinearLayout progressBarLayout;
        LinearLayout addressLayoutField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShopImageField = itemView.findViewById(R.id.iv_shop_image);
            tvShopNameField = itemView.findViewById(R.id.tv_shop_name_field);
            tvPhoneNoField = itemView.findViewById(R.id.tv_phone_no_field);
            tvViewAddressField = itemView.findViewById(R.id.tv_view_address_field);
            ivFavouriteImageField = itemView.findViewById(R.id.iv_favourite_image);
            ivCallIconField = itemView.findViewById(R.id.iv_call_field);
            ivMessageField = itemView.findViewById(R.id.iv_message_field);
            addressLayoutField = itemView.findViewById(R.id.address_layout_field);

            progressBarLayout = itemView.findViewById(R.id.progress_layout_field);
            ivShopImageField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductsShopDetailsModel selectedDetails = productList.get(tag);
                callBackListener.callBackReceived(selectedDetails);
            });
            ivCallIconField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductsShopDetailsModel selectedDetails = productList.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_CALL);
                contentModel.setValue(selectedDetails.getShopMobileNo());
                callBackListener.callBackReceived(contentModel);
            });
            ivMessageField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductsShopDetailsModel selectedDetails = productList.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_MESSAGE);
                contentModel.setValue(selectedDetails.getEmailId());
                callBackListener.callBackReceived(contentModel);
            });
            ivFavouriteImageField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductsShopDetailsModel selectedDetails = productList.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_SHOP_FAVOURITE);
                contentModel.setValue(selectedDetails);
                callBackListener.callBackReceived(contentModel);
            });
            addressLayoutField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductsShopDetailsModel selectedDetails = productList.get(tag);
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
                for (CustomerProductsShopDetailsModel customerProductsModel : productList) {
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
            filteredListData = (List<CustomerProductsShopDetailsModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
