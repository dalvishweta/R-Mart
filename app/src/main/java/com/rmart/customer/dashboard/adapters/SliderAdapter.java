package com.rmart.customer.dashboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rmart.R;
import com.rmart.customer.dashboard.model.SliderImages;


import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private ArrayList<SliderImages> sliderImages;
    private ArrayList<Integer> sliderImagesdownload;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapter(Context context, ArrayList<SliderImages> sliderImages) {
        this.context = context;
        this.sliderImages=sliderImages;
        if(context != null){
            inflater = LayoutInflater.from(context);
        }
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    ViewGroup viewGroup;


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    /*  @Override
      public int getCount() {
          return sliderImages==null?0:sliderImages.size();
      }
  */
    @Override
    public int getCount() {
        final int itemsSize = sliderImages==null?0:sliderImages.size();
        return itemsSize > 1 ? itemsSize + 2 : itemsSize;
    }

    public int getCountWithoutFakePages() {
        return sliderImages.size();
    }

    public void setItems(final ArrayList<SliderImages> items) {
        sliderImages = items;
        notifyDataSetChanged();
    }



    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        viewGroup=view;



        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = myImageLayout
                .findViewById(R.id.image_slider);
        SliderImages image;
        if(position == 0) {
            image   =sliderImages.get(sliderImages.size()-1);
        }
        else if(position == sliderImages.size() + 1) {
            image= sliderImages.get(0);
        } else {

            image= sliderImages.get(position - 1);
        }




        String a=image.getImage();
        Glide.with(context)
                .load(a)
                .thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                //.error(R.drawable.error_center_x)
                .into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }







}
