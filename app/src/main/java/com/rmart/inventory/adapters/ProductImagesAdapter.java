package com.rmart.inventory.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.utilits.custom_views.CustomNetworkImageView;
import com.rmart.utilits.pojos.ImageURLResponse;

import java.io.File;
import java.util.List;

/**
 * Created by Satya Seshu on 21/10/20.
 */
public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ViewHolderItem> {

    private final LayoutInflater layoutInflater;
    private final List<ImageURLResponse> imagesList;
    private final ImageLoader imageLoader;

    public ProductImagesAdapter(Context context, List<ImageURLResponse> imagesList) {
        layoutInflater = LayoutInflater.from(context);
        this.imagesList = imagesList;
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.add_product_image_item_view, parent, false);
        return new ViewHolderItem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        ImageURLResponse imageURLResponse = imagesList.get(position);
        String imageUri = imageURLResponse.getImageUri();
        holder.productImageLayoutField.setVisibility(View.VISIBLE);
        holder.ivAddProductImageField.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(imageUri)) {
            File file = new File(imageUri);
            Uri uri = Uri.fromFile(file);
            holder.ivProductImageField.setLocalImageUri(uri);
        } else {
            String productUrl = imageURLResponse.getImageURL();
            if (!TextUtils.isEmpty(productUrl)) {
                int errorImageAlert = android.R.drawable.ic_dialog_alert;
                imageLoader.get(productUrl, ImageLoader.getImageListener(holder.ivProductImageField, errorImageAlert, errorImageAlert));
                holder.ivProductImageField.setImageUrl(productUrl, imageLoader);
            } else {
                holder.productImageLayoutField.setVisibility(View.GONE);
                holder.ivAddProductImageField.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {

        CustomNetworkImageView ivProductImageField;
        AppCompatImageView ivAddProductImageField;
        AppCompatImageView ivDeleteProductImageField;
        RelativeLayout productImageLayoutField;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            ivAddProductImageField = itemView.findViewById(R.id.iv_add_product_image_field);
            ivDeleteProductImageField = itemView.findViewById(R.id.iv_delete_product_image_field);
            productImageLayoutField = itemView.findViewById(R.id.product_image_layout_field);
        }
    }
}
