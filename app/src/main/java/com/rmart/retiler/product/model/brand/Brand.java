package com.rmart.retiler.product.model.brand;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.R;

public class Brand {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    /*@BindingAdapter("imageUrl")
    public static void loadImage(View view, Brand data) {

        ImageView imageview = view.findViewById(R.id.imageview);
        ImageView selectedgreeting = view.findViewById(R.id.loadericon);
        selectedgreeting.setVisibility(View.VISIBLE);
        GlideApp.with(view.getContext()).load(data.image) .listener(new RequestListener<Drawable>() {

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
                signature(new ObjectKey(data.image==null?"":data.image)).
                error(R.mipmap.default_product_image).thumbnail(0.5f).into(imageview);
    }*/
}
