package com.rmart.customer.dashboard.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.dashboard.listners.OnClick;
import com.rmart.customer.dashboard.model.HomePageData;
import com.rmart.databinding.HomePageItemRowBinding;
import com.rmart.databinding.ServicesBinding;
import com.rmart.databinding.SliderBinding;
import com.rmart.utilits.IteamDoration;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_SLIDER = 29792;
    private static final int VIEW_ITEMS = 2922792;
    private static final int VIEW_SERVICE = 292392;

    Activity context;
    OnClick onClickListner;
    List<HomePageData> homePageData;
    public HomeAdapter(Activity context, OnClick onClickListner, List<HomePageData> homePageData) {
        this.context = context;
        this.homePageData = homePageData;

        this.onClickListner=onClickListner;
    }
    @Override
    public int getItemViewType(int position) {
        if(homePageData.get(position).getType().equalsIgnoreCase("sliders")){
            return VIEW_SLIDER;
        }
        if(homePageData.get(position).getType().equalsIgnoreCase("service")){
            return  VIEW_SERVICE;
        }

            return VIEW_ITEMS;


    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==VIEW_SLIDER) {
            SliderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.slider, parent, false);
            SliderHolder vh = new SliderHolder(binding);
            return vh;
        } else if(viewType==VIEW_SERVICE){

            ServicesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.services, parent, false);
            ServicesHolder vh = new ServicesHolder(binding);
            return vh;

        } else {

            HomePageItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.home_page_item_row, parent, false);
            HomePageHolder vh = new HomePageHolder(binding);

            return vh;
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {

        if(holder2 instanceof SliderHolder) {

            SliderHolder holder = (SliderHolder) holder2;

            SliderAdapter imageSliderAdapter = new SliderAdapter(context,homePageData.get(position).getSliders() );
            holder.binding.pager.setAdapter(imageSliderAdapter);
            try {
                holder.binding.indicator.setViewPager(holder.binding.pager);
            } catch (Exception e){

            }
        }
        else if(holder2 instanceof  ServicesHolder){
            ServicesHolder servicesHolder = (ServicesHolder) holder2;
            ServicesAdapter categoryAdapter = new  ServicesAdapter(context, homePageData.get(position).getService(),onClickListner);
            servicesHolder.bind(homePageData.get(position));
           // GridLayoutManager linearLayoutManager =new GridLayoutManager(context,4);
            //linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            LinearLayoutManager linearLayoutManager =new GridLayoutManager(context,3);
            IteamDoration itemDecoration = new IteamDoration(context, R.dimen.item_offset);
            servicesHolder.binding.category.addItemDecoration(itemDecoration);

            servicesHolder.binding.category.setLayoutManager(linearLayoutManager);
            servicesHolder.binding.category.setAdapter(categoryAdapter);

        }
        else {
            HomePageHolder homePageHolder = (HomePageHolder) holder2;
            homePageHolder.bind(homePageData.get(position));
            if(homePageData.get(position).getType().equalsIgnoreCase("ShopTypes")){

                ShopCategoryAdapter categoryAdapter = new  ShopCategoryAdapter(context,homePageData.get(position).getShopTypes(),onClickListner);
                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                homePageHolder.binding.category.setLayoutManager(linearLayoutManager);
                homePageHolder.binding.category.setAdapter(categoryAdapter);
            }
            else if(homePageData.get(position).getType().equalsIgnoreCase("bigShopType")){

                BigShopCategoryAdapter categoryAdapter = new  BigShopCategoryAdapter(context,homePageData.get(position).getBigShopType(), onClickListner);
                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                homePageHolder.binding.category.setLayoutManager(linearLayoutManager);
                homePageHolder.binding.category.setAdapter(categoryAdapter);
            }
           else
            if(homePageData.get(position).getType().equalsIgnoreCase("offers")){

                AdvertiseAdapter categoryAdapter = new  AdvertiseAdapter(context,homePageData.get(position).getOffers());
                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                homePageHolder.binding.category.setLayoutManager(linearLayoutManager);
                homePageHolder.binding.category.setAdapter(categoryAdapter);
            }

        }





    }
    @Override
    public int getItemCount() {
        return homePageData.size();
    }

    public class SliderHolder extends RecyclerView.ViewHolder {

        SliderBinding binding;

        public SliderHolder(SliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.shopDetails, obj);
            binding.executePendingBindings();
        }
    }
    public class HomePageHolder extends RecyclerView.ViewHolder {

        HomePageItemRowBinding binding;

        public HomePageHolder(HomePageItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.HomePageData, obj);
            binding.executePendingBindings();
        }
    }

    public class ServicesHolder extends RecyclerView.ViewHolder {

        ServicesBinding binding;

        public ServicesHolder(ServicesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.HomePageData, obj);
            binding.executePendingBindings();
        }
    }


}