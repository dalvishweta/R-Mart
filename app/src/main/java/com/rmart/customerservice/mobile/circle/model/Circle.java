package com.rmart.customerservice.mobile.circle.model;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.annotations.SerializedName;
import com.rmart.R;
import com.rmart.glied.GlideApp;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

public class Circle {
    @SerializedName("name")
   public String name;
    @SerializedName("value")
    public String value;

    public Circle(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
