package com.rmart.customer.shops.home.adapters;


import android.app.Activity;
import android.graphics.Paint;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.customer.adapters.CustomSpinnerAdapter;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.shops.home.fragments.ShopHomePage;
import com.rmart.customer.shops.home.listner.OnClickListner;
import com.rmart.customer.shops.home.model.Category;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.home.model.Results;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.customer.shops.products.repositories.ProductsRepository;
import com.rmart.databinding.LoadMoreItemBinding;
import com.rmart.databinding.ProductItemsBinding;
import com.rmart.databinding.ShopHomePageBinding;
import com.rmart.databinding.ShopHomePageCategoryItemsBinding;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.UpdateCartCountDetails;
import com.rmart.utilits.Utils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
    ShopDetailsModel vendorShopDetails;

    public ProductsAdapter(Activity context, ArrayList<ProductData> productDatas,OnClickListner onClickListner,boolean isHomePageList,ShopDetailsModel vendorShopDetails) {
        this.context = context;
        this.isHomePageList = isHomePageList;
        this.vendorShopDetails = vendorShopDetails;
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
                myViewHolder.binding.topview.getLayoutParams().width = (width / 3) - 15;
            }
            List<CustomerProductsDetailsUnitModel>  units = productData.get(position).getUnits();
            myViewHolder.binding.quantitySpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object lSelectedItem = myViewHolder.binding.quantitySpinnerField.getSelectedItem();
                        if (lSelectedItem instanceof CustomerProductsDetailsUnitModel) {
                            CustomerProductsDetailsUnitModel productUnitDetails = (CustomerProductsDetailsUnitModel) lSelectedItem;
                            setUnit(myViewHolder, productUnitDetails);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            if (units != null && !units.isEmpty()) {
                    ArrayList<Object> updatedUnitsList = new ArrayList<>(units);
                    CustomSpinnerAdapter unitsAdapter = new CustomSpinnerAdapter(context, updatedUnitsList,true);
                    myViewHolder.binding.quantitySpinnerField.setAdapter(unitsAdapter);
             }
            myViewHolder.binding.outofstock.getLayoutParams().height = myViewHolder.binding.topview.getLayoutParams().height;
            myViewHolder.binding.topview.setOnClickListener(view -> onClickListner.onProductSelected(productData.get(position)));
            myViewHolder.binding.addtocart.setOnClickListener(view -> {
                Object lSelectedItem = myViewHolder.binding.quantitySpinnerField.getSelectedItem();
                addtocart((CustomerProductsDetailsUnitModel)lSelectedItem,productData.get(position),position,1);
            });
            myViewHolder.binding.btnAddField.setOnClickListener(view -> {
                Object lSelectedItem = myViewHolder.binding.quantitySpinnerField.getSelectedItem();
                addtocart((CustomerProductsDetailsUnitModel)lSelectedItem,productData.get(position),position,1);
            });
            myViewHolder.binding.btnMinusField.setOnClickListener(view -> {

                Object lSelectedItem = myViewHolder.binding.quantitySpinnerField.getSelectedItem();
                addtocart((CustomerProductsDetailsUnitModel)lSelectedItem,productData.get(position),position,2);
            });
        }
    }

    public void addtocart(CustomerProductsDetailsUnitModel unitModel,ProductData productData2,int position,int type){

        boolean perform= true;
        if(type==1) {

            unitModel.setTotalProductCartQty(unitModel.getTotalProductCartQty() + 1);
        }else{
            if(unitModel.getTotalProductCartQty()>0) {
                unitModel.setTotalProductCartQty(unitModel.getTotalProductCartQty() - 1);
            } else {
                perform =false;
            }
        }
        if(perform) {
            ProductsRepository.addToCart(vendorShopDetails.getVendorId(), MyProfile.getInstance().getUserID(), unitModel.getProductUnitId(), unitModel.getTotalProductCartQty(), "").observeForever(new Observer<AddToCartResponseDetails>() {
                @Override
                public void onChanged(AddToCartResponseDetails addToCartResponseDetails) {
                    if (addToCartResponseDetails.getStatus().equalsIgnoreCase("success")) {
                        AddToCartResponseDetails.AddToCartDataResponse addToCartDataResponse = addToCartResponseDetails.getAddToCartDataResponse();
                        if (addToCartDataResponse != null) {
                            Integer totalCartCount = addToCartDataResponse.getTotalCartCount();
                            UpdateCartCountDetails.updateCartCountDetails.onNext(totalCartCount);
                            notifyItemChanged(position);


                        } else {
                            if (type == 1) {
                                unitModel.setTotalProductCartQty(unitModel.getTotalProductCartQty() - 1);
                                notifyItemChanged(position);
                            } else {
                                unitModel.setTotalProductCartQty(unitModel.getTotalProductCartQty() + 1);
                                notifyItemChanged(position);
                            }
                        }
                        Toast.makeText(context, addToCartResponseDetails.getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, addToCartResponseDetails.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void setUnit(ProductHolder myViewHolder, CustomerProductsDetailsUnitModel unitModel) {


            myViewHolder.binding.offerlabel.setText(String.format("%s", unitModel.getProductDiscount(), "0.00")+"% Off");
            myViewHolder.binding.unitPrice.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getUnitPrice(), "0.00")));
            myViewHolder.binding.sellingPrice.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getSellingPrice(), "0.00")));
           // myViewHolder.binding.sellingPrice2.setText(String.format("Rs. %s", Utils.roundOffDoubleValue(unitModel.getSellingPrice(), "0.00")));
            myViewHolder.binding.unitPrice.setPaintFlags(myViewHolder.binding.unitPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if(unitModel.getProductDiscount()>0) {
                myViewHolder.binding.offerlabel.setVisibility(View.VISIBLE);
                myViewHolder.binding.unitPrice.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.binding.offerlabel.setVisibility(View.GONE);
                myViewHolder.binding.unitPrice.setVisibility(View.GONE);
            }
            myViewHolder.binding.addtocart.setVisibility(unitModel.getTotalProductCartQty()>0?View.GONE:View.VISIBLE);
            myViewHolder.binding.llQty.setVisibility(unitModel.getTotalProductCartQty()>0?View.VISIBLE:View.GONE);
            myViewHolder.binding.tvNoOfQuantityField.setText(unitModel.getTotalProductCartQty()+"");


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
