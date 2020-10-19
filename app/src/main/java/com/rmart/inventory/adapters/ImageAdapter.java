package com.rmart.inventory.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.pojos.ImageURLResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private final LayoutInflater mLayoutInflater;
    private final List<ImageURLResponse> imagesList;
    private final ImageLoader imageLoader;
    private CallBackInterface callBackListener;

    public ImageAdapter(Context context, List<ImageURLResponse> imagesList) {
        this.imagesList = imagesList;
        mLayoutInflater = LayoutInflater.from(context);
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    public void setCallBackListener(CallBackInterface callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.product_image, container, false);

        NetworkImageView ivProductImageField = itemView.findViewById(R.id.item_img);
        ImageView ivPlayIconImageField = itemView.findViewById(R.id.iv_play_icon_field);
        ImageURLResponse imageUrlResponse = imagesList.get(position);
        boolean isProductVideoSelected = imageUrlResponse.isProductVideoSelected();
        ivPlayIconImageField.setVisibility(isProductVideoSelected ? View.VISIBLE : View.GONE);
        String imageUrl = imageUrlResponse.getDisplayImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(imageUrl, ImageLoader.getImageListener(ivProductImageField, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
            ivProductImageField.setImageUrl(imageUrl, RMartApplication.getInstance().getImageLoader());
        }

        ivProductImageField.setTag(position);
        ivProductImageField.setTag(isProductVideoSelected ? position : -1);
        ivProductImageField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            if(tag != -1 && callBackListener != null) {
                ImageURLResponse selectedItem = imagesList.get(tag);
                callBackListener.callBackReceived(selectedItem);
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((View) object);
    }
}
