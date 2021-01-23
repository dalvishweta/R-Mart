package com.rmart.retiler.inventory.product_from_library.model;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.R;
import com.rmart.glied.GlideApp;


import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable
{
    @SerializedName("product_lib_id")
    @Expose
    private int productLibId;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("category_id")
    @Expose
    private int categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("brand_id")
    @Expose
    private int brandId;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("images")
    @Expose
    private ArrayList<ProductImage> images = null;

    public int getProductLibId() {
        return productLibId;
    }

    public void setProductLibId(int productLibId) {
        this.productLibId = productLibId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public ArrayList<ProductImage> getImages() {

        return images;
    }

    public void setImages(ArrayList<ProductImage> images) {
        this.images = images;
    }


    @BindingAdapter("imageUrl")
    public static void loadImage(View view, Product data) {

        ImageView imageview = view.findViewById(R.id.imageview);
        ImageView selectedgreeting = view.findViewById(R.id.loadericon);
        selectedgreeting.setVisibility(View.VISIBLE);
        GlideApp.with(view.getContext()).load(data.productImage) .listener(new RequestListener<Drawable>() {
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
                signature(new ObjectKey(data.productImage==null?"":data.productImage)).
                error(R.mipmap.default_product_image).thumbnail(0.5f).into(imageview);
    }

}


