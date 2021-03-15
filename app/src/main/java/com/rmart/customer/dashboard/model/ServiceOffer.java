package com.rmart.customer.dashboard.model;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

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

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

public class ServiceOffer {
    @SerializedName("service_caption")
    @Expose
    private String serviceCaption;
    @SerializedName("name_image")
    @Expose
    private String nameImage;
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public String getServiceCaption() {
        return serviceCaption;
    }

    public void setServiceCaption(String serviceCaption) {
        this.serviceCaption = serviceCaption;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(View view, ServiceOffer data) {

        ImageView imageview = view.findViewById(R.id.imageview);
        ImageView selectedgreeting = view.findViewById(R.id.selectedgreeting);
        selectedgreeting.setVisibility(View.VISIBLE);
        GlideApp.with(view.getContext()).load(data.nameImage) .listener(new RequestListener<Drawable>() {

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
                signature(new ObjectKey(data.nameImage==null?"":data.nameImage)).
                error(R.mipmap.default_product_image).thumbnail(0.5f).into(imageview);
    }
}