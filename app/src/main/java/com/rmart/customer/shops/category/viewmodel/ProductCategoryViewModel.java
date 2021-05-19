package com.rmart.customer.shops.category.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.rmart.R;
import com.rmart.customer.shops.category.model.Category;
import com.rmart.customer.shops.category.model.CategoryBaseResponse;
import com.rmart.customer.shops.category.repositories.ProductCategoryRepository;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.glied.GlideApp;

public class ProductCategoryViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<CategoryBaseResponse> productCategoryMutableLiveData = new MutableLiveData<>();



    public void loadCategoryData(Context context, ShopDetailsModel productsShopDetailsModel,String Cat_ids,int Parent_id){
        isLoading.setValue(true);
        ProductCategoryRepository.getProductCategory(productsShopDetailsModel.getClientId(),productsShopDetailsModel.getVendorId(),Cat_ids,Parent_id).observeForever(hotelResult -> {
            productCategoryMutableLiveData.setValue(hotelResult);
            isLoading.postValue(false);
        });




    }
    @BindingAdapter("imageUrl")
    public static void loadImage(View view, Category data) {
        if(data!=null) {
            ImageView imageview = view.findViewById(R.id.imageview);
            ImageView selectedgreeting = view.findViewById(R.id.selectedgreeting);
            selectedgreeting.setVisibility(View.VISIBLE);
            GlideApp.with(view.getContext()).load(data.getParentImage()).listener(new RequestListener<Drawable>() {

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
                    signature(new ObjectKey(data.getParentImage())).
                    error(R.mipmap.default_product_image).thumbnail(0.5f).into(imageview);
        }
    }

}
