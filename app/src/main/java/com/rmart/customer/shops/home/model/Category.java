package com.rmart.customer.shops.home.model;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.annotations.SerializedName;
import com.rmart.R;
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.glied.GlideApp;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

public class Category  implements Serializable {

    @SerializedName("parent_category_name")
    public String parentCategoryName ="";
    @SerializedName("category_ids")
    public String categoryIds ="";
    @SerializedName("parent_category_id")
    public String parentCategoryId ;
    @SerializedName("subcategory_id")
    public String subcategory_id;
    @SerializedName("parent_image")
    public String parentImage ="";
    @SerializedName("category_desc")
    public String categoryDesc ="";
    @SerializedName("category_name")
    public String categoryName ="";

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(View view, Category data) {

        ImageView imageview = view.findViewById(R.id.imageview);
        ImageView selectedgreeting = view.findViewById(R.id.selectedgreeting);
        selectedgreeting.setVisibility(View.VISIBLE);
        GlideApp.with(view.getContext()).load(data.parentImage) .listener(new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                selectedgreeting.setVisibility(View.GONE);

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                selectedgreeting.setVisibility(View.GONE);
                return false;
            }
        }).dontAnimate().
                diskCacheStrategy(DiskCacheStrategy.ALL).
                signature(new ObjectKey(data.parentImage==null?"":data.parentImage)).
                error(R.mipmap.applogo).thumbnail(0.5f).into(imageview);
    }
}
