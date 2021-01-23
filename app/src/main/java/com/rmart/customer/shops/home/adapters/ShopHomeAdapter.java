package com.rmart.customer.shops.home.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.shops.home.fragments.ShopHomePage;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.Results;
import com.rmart.customer.shops.list.models.CustomerProductsShopDetailsModel;
import com.rmart.databinding.ShopDetailsPageBinding;
import com.rmart.databinding.ShopHomePageBinding;
import com.rmart.deeplinking.LinkGenerator;
import com.rmart.utilits.GridSpacesItemDecoration;
import com.rmart.utilits.Permisions;
import com.rmart.utilits.Utils;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class ShopHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int VIEW_MENU=1111;
    final int VIEW_SHOP=333311;
    public ArrayList<Category> category;
    ArrayList<Results> results = new ArrayList<Results>();
    Activity context;
    OnClickListner onClickListner;
    CustomerProductsShopDetailsModel productsShopDetailsModel;

    public ShopHomeAdapter(Activity context, ArrayList<Results> results, CustomerProductsShopDetailsModel productsShopDetailsModel,OnClickListner onClickListner) {
        this.context = context;
        this.productsShopDetailsModel = productsShopDetailsModel;
        this.results=results;
        this.onClickListner=onClickListner;
    }
    @Override
    public int getItemViewType(int position) {
        if( position==0){
            return VIEW_SHOP;
        } else {
            return VIEW_MENU;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType==VIEW_SHOP) {
                    ShopDetailsPageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.shop_details_page, parent, false);
                    ShopHolder vh = new ShopHolder(binding);
                    // pass the view to View Holder
                    return vh;
                } else {
                    ShopHomePageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.shop_home_page, parent, false);
                    MyViewHolder vh = new MyViewHolder(binding); // pass the view to View Holder
                    return vh;
                }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {
        if(holder2 instanceof ShopHolder) {
            ShopHolder myViewHolder=     (ShopHolder ) holder2;
            myViewHolder.bind(productsShopDetailsModel);
            myViewHolder.binding.ivShareField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Permisions.checkWriteExternlStoragePermission((Activity) myViewHolder.binding.getRoot().getContext())) {
                        Bitmap bitmap = null;
                        try {
                            myViewHolder.binding.shopiamge.setDrawingCacheEnabled(true);
                            bitmap = Bitmap.createBitmap(myViewHolder.binding.shopiamge.getDrawingCache());
                            myViewHolder.binding.shopiamge.setDrawingCacheEnabled(false);

                        } catch (Exception e){
                            Toast.makeText(     myViewHolder.binding.shopiamge.getContext(),"pont1",Toast.LENGTH_LONG).show();
                        }

                        String message= "रोकड मार्ट आता आपल्या शहरामध्ये!!!\n" +
                                "आता "+productsShopDetailsModel.getShopName()+"शॉप रोकड मार्ट सोबत ऑनलाईन झाले आहे. \n" +
                                "नवीन ऑफर्स आणि शॉपिंग साठी खालील लिंक वर क्लिक करा आणि अँप डाउनलोड करा.\n" ;
                        String deeplink = "https://www.rokadmart.com/public/Home/index?shop_id="+productsShopDetailsModel.getShopId()+"&client_id=2&created_by="+productsShopDetailsModel.getVendorId();
                        LinkGenerator.shareLink(context,message,bitmap,deeplink);


                    } else {
                        Permisions.requestWriteExternlStoragePermission(myViewHolder.binding.getRoot().getContext());
                    }
                }
            });
            myViewHolder.binding.ivCallField.setOnClickListener(view -> {
                Utils.openDialPad(view.getContext(),productsShopDetailsModel.getShopMobileNo());
            });
            myViewHolder.binding.ivMessageField.setOnClickListener(view -> {
                Utils.openGmailWindow(view.getContext(),productsShopDetailsModel.getEmailId());
            });
        }
        if(holder2 instanceof MyViewHolder) {
            MyViewHolder myViewHolder=     (MyViewHolder ) holder2;
            Results rs = results.get(position-1);
            myViewHolder.bind(rs);
            if(rs.type.equalsIgnoreCase("category")){
                category = rs.category;
                CategoryAdapter categoryAdapter = new  CategoryAdapter(context,rs.category,onClickListner);
                myViewHolder.binding.category.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL,false));
                myViewHolder.binding.category.setAdapter(categoryAdapter);
                myViewHolder.binding.viewall.setVisibility(View.GONE);


            } else if(rs.type.equalsIgnoreCase("product_data")) {
                ProductsAdapter productsAdapter = new  ProductsAdapter(context,rs.productData,onClickListner,true);
                myViewHolder.binding.category.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL,false));
                myViewHolder.binding.category.setAdapter(productsAdapter);
                myViewHolder.binding.category.addItemDecoration(new GridSpacesItemDecoration(10));
                myViewHolder.binding.viewall.setVisibility(View.VISIBLE);
            }
            myViewHolder.binding.viewall.setOnClickListener(view -> {
                for (Category categorye:category ) {
                    if(categorye.parentCategoryName.equalsIgnoreCase(rs.name)){
                        onClickListner.onCategorySelected(categorye);
                        break;
                    }
                }
            });


        }
    }
    @Override
    public int getItemCount() {
        return results.size()+1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ShopHomePageBinding binding;
        public MyViewHolder(ShopHomePageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.result, obj);
            binding.executePendingBindings();
        }
    }

    public class ShopHolder extends RecyclerView.ViewHolder {

        ShopDetailsPageBinding binding;

        public ShopHolder(ShopDetailsPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.shopDetails, obj);
            binding.executePendingBindings();
        }
    }


}