package com.rmart.customer.shops.home.viewmodel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.rmart.R;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.home.model.ShopHomePageResponce;
import com.rmart.customer.shops.home.repositories.ShopRepository;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.glied.GlideApp;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopHomeViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<ShopHomePageResponce> shopHomePageResponceMutableLiveData = new MutableLiveData<>();

    @BindingAdapter("app:shopimage")
    public static void setShopImage(RelativeLayout view, Object image) {
        String data;
        if(image instanceof ArrayList){
            data= (String) ((ArrayList) image).get(0);
        } else {
            data= (String) image;
        }

        ImageView imageview = view.findViewById(R.id.shopiamge);
        ImageView selectedgreeting = view.findViewById(R.id.loader);
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(data==null?"":data)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageview.setImageBitmap(resource);
                        selectedgreeting.setVisibility(View.GONE);


                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        selectedgreeting.setVisibility(View.GONE);

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        selectedgreeting.setVisibility(View.GONE);

                    }

                });

    }

    public void loadShopHomePage(ShopDetailsModel productsShopDetailsModel){
        isLoading.setValue(true);
        ShopRepository.getShopHomePage(productsShopDetailsModel.getVendorId(),productsShopDetailsModel.getShopId()).observeForever(hotelResult -> {
            shopHomePageResponceMutableLiveData.setValue(hotelResult);
            isLoading.postValue(false);
        });




    }
    @BindingAdapter("imageUrl")
    public static void loadImage(View view, ProductData data) {
        if(data!=null) {
            ImageView imageview = view.findViewById(R.id.imageview);
            ImageView selectedgreeting = view.findViewById(R.id.selectedgreeting);
            selectedgreeting.setVisibility(View.VISIBLE);
            GlideApp.with(view.getContext()).load(data.productImage).listener(new RequestListener<Drawable>() {

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
                    signature(new ObjectKey(data.productImage)).
                    error(R.mipmap.default_product_image).thumbnail(0.5f).into(imageview);
        }
    }

}
