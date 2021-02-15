package com.rmart.customer.shops.home.adapters;


import android.app.Activity;
import android.graphics.Paint;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.shops.home.fragments.ShopHomePage;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.home.model.Results;
import com.rmart.databinding.LoadMoreItemBinding;
import com.rmart.databinding.ProductItemsBinding;
import com.rmart.databinding.ShopHomePageBinding;
import com.rmart.databinding.ShopHomePageCategoryItemsBinding;
import com.rmart.utilits.Utils;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final public int VIEW_PRODUCT=112222;
    final public int VIEW_LOADING=223233;

   public ArrayList<ProductData> productData = new ArrayList<>();
    Activity context;
    boolean isHomePageList,isLoading;
    OnClickListner onClickListner;

    public ProductsAdapter(Activity context, ArrayList<ProductData> productDatas,OnClickListner onClickListner,boolean isHomePageList) {
        this.context = context;
        this.isHomePageList = isHomePageList;
        this.onClickListner = onClickListner;
        this.productData =(productDatas);
    }
    public void addProducts(ArrayList<ProductData> productDatas){
        this.productData.addAll(productDatas);
        notifyDataSetChanged();
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        notifyItemChanged(productData.size());
    }

    @Override
    public int getItemViewType(int position) {

        if(isLoading && productData.size()==position) {
            return VIEW_LOADING;
        }
        return VIEW_PRODUCT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==VIEW_LOADING) {
            LoadMoreItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.load_more_item, parent, false);
            LoadingHolder vh = new LoadingHolder(binding); // pass the view to View Holder
            return vh;
        }  else {
            ProductItemsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_items, parent, false);
            ProductHolder vh = new ProductHolder(binding); // pass the view to View Holder
            return vh;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {

        if(holder2 instanceof ProductHolder) {
            ProductHolder myViewHolder=     (ProductHolder ) holder2;
            myViewHolder.bind(productData.get(position));
            if(isHomePageList) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                myViewHolder.binding.topview.getLayoutParams().width = (width / 2) - 15;
            }
            List<CustomerProductsDetailsUnitModel>  units = productData.get(position).getUnits();
            if(units!=null){

                for (int i = units.size()-1;i>=0;i-- ) {

                    CustomerProductsDetailsUnitModel unitModel =  units.get(i);

                    myViewHolder.binding.offerlabel.setText(String.format("%s", unitModel.getProductDiscount(), "0.00")+"% Off");
                    myViewHolder.binding.unitPrice.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getUnitPrice(), "0.00")));
                    myViewHolder.binding.sellingPrice.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getSellingPrice(), "0.00")));
                    myViewHolder.binding.sellingPrice2.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getSellingPrice(), "0.00")));
                    myViewHolder.binding.unitPrice.setPaintFlags(myViewHolder.binding.unitPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    if(unitModel.getUnitPrice() -unitModel.getSellingPrice()==0 ){
                       myViewHolder.binding.priceOffer.setVisibility(View.GONE);
                       myViewHolder.binding.sellingPrice2.setVisibility(View.VISIBLE);
                    } else {
                       myViewHolder.binding.priceOffer.setVisibility(View.VISIBLE);
                       myViewHolder.binding.sellingPrice2.setVisibility(View.GONE);
                    }

                    if(unitModel.getProductDiscount()>0) {
                        myViewHolder.binding.offerlabel.setVisibility(View.VISIBLE);
                        break;
                    } else {

                        myViewHolder.binding.offerlabel.setVisibility(View.GONE);
                    }


                }
            }
            myViewHolder.binding.outofstock.getLayoutParams().height = myViewHolder.binding.topview.getLayoutParams().height;
            myViewHolder.binding.topview.setOnClickListener(view -> onClickListner.onProductSelected(productData.get(position)));
        }
    }
    @Override
    public int getItemCount() {
        if(isLoading){
            return productData.size()+1;
        }
        if(productData!=null){
            return productData.size();
        } else {
            return 0;
        }

    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        ProductItemsBinding binding;

        public ProductHolder(ProductItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.productdata, obj);
            binding.executePendingBindings();
        }
    }
    public class LoadingHolder extends RecyclerView.ViewHolder {

        LoadMoreItemBinding binding;

        public LoadingHolder(LoadMoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.productdata, obj);
            binding.executePendingBindings();
        }
    }
}
