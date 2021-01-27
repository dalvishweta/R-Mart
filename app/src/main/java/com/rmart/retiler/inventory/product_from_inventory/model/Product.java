package com.rmart.retiler.inventory.product_from_inventory.model;

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

import java.util.List;

public class Product {

        @SerializedName("product_id")
        @Expose
        private int productId;
        @SerializedName("product_cat_id")
        @Expose
        private int productCatId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_details")
        @Expose
        private String productDetails;
        @SerializedName("display_image")
        @Expose
        private String displayImage;
        @SerializedName("brand_id")
        @Expose
        private int brandId;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("images")
        @Expose
        private List<ProductImage> images = null;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getProductCatId() {
            return productCatId;
        }

        public void setProductCatId(int productCatId) {
            this.productCatId = productCatId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
        }

        public String getDisplayImage() {
            return displayImage;
        }

        public void setDisplayImage(String displayImage) {
            this.displayImage = displayImage;
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

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<ProductImage> getImages() {
            return images;
        }

        public void setImages(List<ProductImage> images) {
            this.images = images;
        }


    @BindingAdapter("imageUrl")
    public static void loadImage(View view, Product data) {

        ImageView imageview = view.findViewById(R.id.imageview);
        ImageView selectedgreeting = view.findViewById(R.id.loadericon);
        selectedgreeting.setVisibility(View.VISIBLE);
        GlideApp.with(view.getContext()).load(data.displayImage) .listener(new RequestListener<Drawable>() {
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
                signature(new ObjectKey(data.displayImage==null?"":data.displayImage)).
                error(R.mipmap.default_product_image).thumbnail(0.5f).into(imageview);
    }


}

