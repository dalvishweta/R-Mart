package com.rmart.customerservice.mobile.operators.model;

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
import com.google.gson.annotations.SerializedName;
import com.rmart.R;
import com.rmart.glied.GlideApp;

import java.io.Serializable;

public class Operator implements Serializable {
    @SerializedName("operator_name")
   public String name;

    @SerializedName("img_path")
    public String image;
    @SerializedName("operator")
    public String type;
    @SerializedName("mplan_operator")
    public String mplanOperator;


    @BindingAdapter("imageUrl")
    public static void loadImage(View view, Operator data) {

        try {
            ImageView imageview = view.findViewById(R.id.imageview);
            ImageView selectedgreeting = view.findViewById(R.id.selectedgreeting);
            selectedgreeting.setVisibility(View.VISIBLE);
            GlideApp.with(view.getContext()).load(data.image).listener(new RequestListener<Drawable>() {

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
                    signature(new ObjectKey(data.image == null ? "" : data.image)).
                    error(R.mipmap.default_product_image).thumbnail(0.5f).into(imageview);
        } catch (Exception e){

        }
    }
}
