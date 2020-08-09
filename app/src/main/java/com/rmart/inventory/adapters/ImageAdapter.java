package com.rmart.inventory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.rmart.R;
import com.rmart.inventory.views.viewholders.CategoryViewHolder;
import com.rmart.inventory.views.viewholders.ImageViewHolder;

public class ImageAdapter extends PagerAdapter {
    Context context;
    private int[] GalImages = new int[] {
            R.drawable.item_image,
            R.drawable.item_image,
            R.drawable.item_image
    };

    LayoutInflater mLayoutInflater;

    public ImageAdapter(Context context){
        this.context=context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.product_image, container, false);

        ImageView imageView = itemView.findViewById(R.id.item_img);
        imageView.setImageResource(GalImages[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
