package com.rmart.retiler.productstatus.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.inventory.views.BaseInventoryFragment;
import com.rmart.retiler.inventory.product_from_inventory.model.Product;
import com.rmart.retiler.productstatus.UdateListner;
import com.rmart.retiler.productstatus.activities.ProductFromInvetoryList;

import java.util.ArrayList;

public class ProductPagerAdapter extends FragmentStatePagerAdapter {


    public ArrayList<Product> products;
    Context context;
    ProductFromInvetoryList productFromInvetoryListYES,productFromInvetoryListNO;
    public OnInventoryClickedListener mListener;
    private ArrayList<BaseInventoryFragment> rechargePlansFrags;
    private ArrayList<String> fragTitles;
    public ProductPagerAdapter(@NonNull FragmentManager fm, int behavior)
    {
        super(fm,behavior);
        rechargePlansFrags = new ArrayList<>();
        fragTitles = new ArrayList<>();
        fragTitles.add("Active Products");
        fragTitles.add("Inactive Products");
        productFromInvetoryListYES =ProductFromInvetoryList.newInstance("YES");
        productFromInvetoryListNO =ProductFromInvetoryList.newInstance("NO");
        productFromInvetoryListYES.setUdateListner(() -> {
            productFromInvetoryListNO.onUpdate();
        });
        productFromInvetoryListNO.setUdateListner(() -> {
            productFromInvetoryListYES.onUpdate();
        });
        rechargePlansFrags.add(productFromInvetoryListYES);
        rechargePlansFrags.add(productFromInvetoryListNO);
    }
    public void  onTextChange(String searchText){
        try {
            productFromInvetoryListYES.onTextChange(searchText);
            productFromInvetoryListNO.onTextChange(searchText);
        }catch (Exception e){

        }
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return rechargePlansFrags.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("title", String.valueOf(fragTitles.size()));
        return fragTitles.get(position);
    }

    public ArrayList<String> getFragTitles() {
        return fragTitles;
    }


    @Override
    public int getCount() {
        return rechargePlansFrags.size();
    }


}
