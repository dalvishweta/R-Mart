package com.rmart.inventory.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.inventory.views.viewholders.ImageUploadVH;

import java.util.ArrayList;

public class ImageUploadAdapter extends RecyclerView.Adapter<ImageUploadVH> {
    public static final String DEFAULT = "default";
    ArrayList<String> imagePath;
    View.OnClickListener onClickListener;
    public ImageUploadAdapter(ArrayList<String> imagePath, View.OnClickListener onClickListener) {
        this.imagePath = imagePath;
        if(imagePath.size()<5) {
            imagePath.add(DEFAULT);
        }
        this.onClickListener = onClickListener;
    }
    @NonNull
    @Override
    public ImageUploadVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.update_image, parent, false);
        return new ImageUploadVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageUploadVH holder, int position) {
        if(imagePath.get(position).equals(DEFAULT)) {
            holder.editView.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.drawable.add);
        } else {
            holder.editView.setImageResource(R.drawable.delete);
            holder.imageView.setImageResource(R.drawable.item_product);
            holder.editView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {

        return imagePath.size();
    }
}
