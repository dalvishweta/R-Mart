package com.rmart.customer.shops.products.viewmodel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
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
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.customer.shops.products.model.ProductsResponce;
import com.rmart.customer.shops.products.repositories.ProductsRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductListViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<ProductsResponce> shopHomePageResponceMutableLiveData = new MutableLiveData<>();


    public void loadProductList(CustomerProductsShopDetailsModel productsShopDetailsModel, String categoeryid, String searchPrase,String start_page,String sub_category_id){
        isLoading.setValue(true);
        ProductsRepository.getVenderProducts(productsShopDetailsModel.getVendorId(),productsShopDetailsModel.getShopId(), categoeryid, searchPrase,start_page, sub_category_id).observeForever(productResult -> {
            shopHomePageResponceMutableLiveData.setValue(productResult);
            isLoading.postValue(false);
        });




    }


}
