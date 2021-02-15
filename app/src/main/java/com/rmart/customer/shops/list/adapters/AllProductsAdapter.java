package com.rmart.customer.shops.list.adapters;

import android.app.Activity;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.listner.onProdcutClick;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.list.models.SearchProducts;
import com.rmart.databinding.LoadMoreItemBinding;
import com.rmart.databinding.ProductItemsBinding;
import com.rmart.databinding.SearchShopProductRowItemBinding;
import com.rmart.utilits.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class AllProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final public int VIEW_PRODUCT=112222;
    final public int VIEW_LOADING=223233;

    public ArrayList<SearchProducts> productData = new ArrayList<>();
    Activity context;
    boolean isLoading;
    onProdcutClick onClickListner;

    public AllProductsAdapter(Activity context, ArrayList<SearchProducts> productDatas,onProdcutClick onClickListner) {
        this.context = context;
        this.onClickListner = onClickListner;
        this.productData =(productDatas);
    }
    public void addProducts(ArrayList<SearchProducts> productDatas){

        this.productData.addAll(productDatas);
        notifyDataSetChanged();
    }
    public void  clear(){
        this.productData.clear();
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
            SearchShopProductRowItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_shop_product_row_item, parent, false);
            ProductHolder vh = new ProductHolder(binding); // pass the view to View Holder
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder2, int position) {

        if(holder2 instanceof ProductHolder) {
            ProductHolder myViewHolder=     (ProductHolder) holder2;
            myViewHolder.bind(productData.get(position));

            List<CustomerProductsDetailsUnitModel> units = productData.get(position).getProduct_unit();
            if(units!=null){
                myViewHolder.binding.offerlabel.setVisibility(View.VISIBLE);
                myViewHolder.binding.unitPrice.setVisibility(View.VISIBLE);
                myViewHolder.binding.sellingPrice.setVisibility(View.VISIBLE);
                myViewHolder.binding.productUnit.setVisibility(View.VISIBLE);
                for (int i = units.size()-1;i>=0;i-- ) {

                    CustomerProductsDetailsUnitModel unitModel =  units.get(i);

                    myViewHolder.binding.offerlabel.setText(String.format("%s", unitModel.getProductDiscount(), "0.00")+"% Off");
                    myViewHolder.binding.unitPrice.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getUnitPrice(), "0.00")));
                    myViewHolder.binding.sellingPrice.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getSellingPrice(), "0.00")));
                    myViewHolder.binding.unitPrice.setPaintFlags(myViewHolder.binding.unitPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    myViewHolder.binding.productUnit.setText(unitModel.getProductUnitQuantity()+" "+unitModel.getShortUnitMeasure());
                    if(unitModel.getUnitPrice() -unitModel.getSellingPrice()==0 ){
                        myViewHolder.binding.offerlabel.setVisibility(View.GONE);
                        myViewHolder.binding.unitPrice.setVisibility(View.GONE);
                    } else {
                        myViewHolder.binding.offerlabel.setVisibility(View.VISIBLE);
                        myViewHolder.binding.unitPrice.setVisibility(View.VISIBLE);
                    }

                    if(unitModel.getProductDiscount()>0) {
                        myViewHolder.binding.offerlabel.setVisibility(View.VISIBLE);
                        break;
                    } else {

                        myViewHolder.binding.offerlabel.setVisibility(View.GONE);
                    }


                }
            } else {
                myViewHolder.binding.offerlabel.setVisibility(View.GONE);
                myViewHolder.binding.unitPrice.setVisibility(View.GONE);
                myViewHolder.binding.sellingPrice.setVisibility(View.GONE);
                myViewHolder.binding.productUnit.setVisibility(View.GONE);
            }
            myViewHolder.binding.row.setOnClickListener(view -> onClickListner.onSelected(productData.get(position)));

        }
    }
    @Override
    public int getItemCount() {
        if(isLoading){
            return productData.size()+1;
        }
        return productData.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        SearchShopProductRowItemBinding binding;

        public ProductHolder(SearchShopProductRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Object obj) {
            binding.setVariable(BR.searchProduct, obj);
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
            binding.setVariable(BR.searchProduct, obj);
            binding.executePendingBindings();
        }
    }
}
