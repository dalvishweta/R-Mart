package com.rmart.inventory.views.viewholders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;

public class ImageUploadVH extends RecyclerView.ViewHolder {
    public ImageView editView, imageView;
    public ImageUploadVH(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
        editView = itemView.findViewById(R.id.edit);
    }
}
