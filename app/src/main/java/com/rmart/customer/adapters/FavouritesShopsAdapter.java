package com.rmart.customer.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.custom_views.CustomNetworkImageView;

import java.util.List;

/**
 * Created by Satya Seshu on 24/10/20.
 */
public class FavouritesShopsAdapter extends RecyclerView.Adapter<FavouritesShopsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<CustomerProductsShopDetailsModel> listData;
    private final CallBackInterface callBackListener;
    private final ImageLoader imageLoader;

    public FavouritesShopsAdapter(Context context, List<CustomerProductsShopDetailsModel> listData, CallBackInterface callBackListener) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.callBackListener = callBackListener;
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    public void updateItems(List<CustomerProductsShopDetailsModel> listData) {
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

        CustomerProductsShopDetailsModel favouritesListData = listData.get(position);

        holder.tvShopNameField.setText(favouritesListData.getShopName());
        holder.tvViewAddressField.setText(favouritesListData.getShopAddress());
        holder.btnRemoveField.setTag(position);
        holder.shopDetailsLayoutField.setTag(position);
        Object lShopImageObject = favouritesListData.getShopImage();
        if (lShopImageObject instanceof String) {
            String shopImageUrl = (String) lShopImageObject;
            if (!TextUtils.isEmpty(shopImageUrl)) {
                HttpsTrustManager.allowAllSSL();
                imageLoader.get(shopImageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            holder.ivShopImageField.setLocalImageBitmap(bitmap);
                        }
                        holder.progressBarLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        holder.progressBarLayout.setVisibility(View.GONE);
                        holder.ivShopImageField.setBackgroundResource(android.R.drawable
                                .ic_dialog_alert);
                    }
                });
                holder.ivShopImageField.setImageUrl(shopImageUrl, RMartApplication.getInstance().getImageLoader());
            } else {
                holder.progressBarLayout.setVisibility(View.GONE);
            }
        } else {
            holder.progressBarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomNetworkImageView ivShopImageField;
        TextView tvShopNameField;
        TextView tvViewAddressField;
        AppCompatButton btnRemoveField;
        LinearLayout progressBarLayout;
        RelativeLayout shopDetailsLayoutField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShopImageField = itemView.findViewById(R.id.iv_shop_image_field);
            tvShopNameField = itemView.findViewById(R.id.tv_shop_name_field);
            tvViewAddressField = itemView.findViewById(R.id.tv_shop_address_field);
            btnRemoveField = itemView.findViewById(R.id.btn_remove_field);
            progressBarLayout = itemView.findViewById(R.id.progress_layout_field);
            shopDetailsLayoutField = itemView.findViewById(R.id.shop_details_layout_field);

            btnRemoveField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductsShopDetailsModel selectedShop = listData.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_REMOVE);
                contentModel.setValue(selectedShop);
                callBackListener.callBackReceived(contentModel);
            });
            shopDetailsLayoutField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                CustomerProductsShopDetailsModel selectedShop = listData.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_DETAILS);
                contentModel.setValue(selectedShop);
                callBackListener.callBackReceived(contentModel);
            });
        }
    }
}
