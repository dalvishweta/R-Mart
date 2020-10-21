package com.rmart.inventory.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.utilits.custom_views.CustomNetworkImageView;
import com.rmart.utilits.pojos.ImageURLResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 21/10/20.
 */
public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ViewHolderItem> {

    private final LayoutInflater layoutInflater;
    private final List<ImageURLResponse> imagesList;
    private final ImageLoader imageLoader;
    private final Drawable drawable;

    public ProductImagesAdapter(Context context, List<ImageURLResponse> imagesList) {
        layoutInflater = LayoutInflater.from(context);
        this.imagesList = imagesList;
        imageLoader = RMartApplication.getInstance().getImageLoader();
        drawable = ContextCompat.getDrawable(context, R.drawable.add);
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.add_product_image_item_view, parent, false);
        return new ViewHolderItem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        if (position < imagesList.size()) {
            holder.ivProductImageField.setVisibility(View.VISIBLE);
            holder.ivAddProductImageField.setVisibility(View.GONE);
            ImageURLResponse imageURLResponse = imagesList.get(position);
            Uri imageUri = imageURLResponse.getImageUri();
            if (imageUri != null) {
                holder.ivProductImageField.setLocalImageUri(imageUri);
            } else {
                String productUrl = imageURLResponse.getImageURL();
                int errorImageAlert = android.R.drawable.ic_dialog_alert;
                imageLoader.get(productUrl, ImageLoader.getImageListener(holder.ivProductImageField, errorImageAlert, errorImageAlert));
                holder.ivProductImageField.setImageUrl(productUrl, imageLoader);
            }
        } else {
            holder.ivProductImageField.setVisibility(View.GONE);
            holder.ivAddProductImageField.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        int size = imagesList.size();
        if (size < 5) {
            size += 1;
        }
        return size;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {

        CustomNetworkImageView ivProductImageField;
        AppCompatImageView ivAddProductImageField;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            ivAddProductImageField = itemView.findViewById(R.id.iv_add_product_image_field);
        }
    }
}
