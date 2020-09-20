package com.rmart.inventory.adapters;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.utilits.HttpsTrustManager;

import java.util.List;

public class ImageUploadAdapter extends RecyclerView.Adapter<ImageUploadAdapter.ImageUploadViewHolder> {

    public static final String DEFAULT = "default";
    private List<Object> imagesList;
    private CallBackInterface callBackListener;
    private ImageLoader imageLoader;

    public ImageUploadAdapter(List<Object> imagesList, CallBackInterface callBackListener) {
        this.imagesList = imagesList;
        this.callBackListener = callBackListener;
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    public void updateImagesList(List<Object> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImageUploadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.update_image, parent, false);
        return new ImageUploadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageUploadViewHolder holder, int position) {
        Object lObject = imagesList.get(position);
        if (lObject instanceof String) {
            String imagePath = (String) lObject;
            if (!TextUtils.isEmpty(imagePath)) {
                if (imagePath.equalsIgnoreCase(DEFAULT)) {
                    holder.ivProductImageField.setBackgroundResource(R.drawable.add);
                } else {
                    HttpsTrustManager.allowAllSSL();
                    imageLoader.get(imagePath, ImageLoader.getImageListener(holder.ivProductImageField, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
                    holder.ivProductImageField.setImageUrl(imagePath, RMartApplication.getInstance().getImageLoader());
                }
            }
        } else if (lObject instanceof Bitmap) {
            holder.ivProductImageField.setImageBitmap((Bitmap) lObject);
        }

        holder.ivProductImageField.setTag(position);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ImageUploadViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView ivProductImageField;

        public ImageUploadViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            ivProductImageField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                ContentModel contentModel = new ContentModel();
                Object lObject = imagesList.get(tag);
                if (lObject instanceof String) {
                    String imagePath = (String) lObject;
                    if (imagePath.equalsIgnoreCase(DEFAULT)) {
                        contentModel.setStatus(Constants.TAG_UPLOAD_NEW_PRODUCT_IMAGE);
                    } else {
                        contentModel.setStatus(Constants.TAG_EDIT_PRODUCT_IMAGE);
                    }
                } else if (lObject instanceof Bitmap) {
                    contentModel.setStatus(Constants.TAG_EDIT_PRODUCT_IMAGE);
                }
                contentModel.setValue(tag);
                callBackListener.callBackReceived(contentModel);
            });
        }
    }
}
