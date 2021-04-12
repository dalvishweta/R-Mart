package com.rmart.retiler.productstatus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.ItemRowProductStatusBinding;
import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.retiler.inventory.product_from_inventory.model.Product;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductImage;
import com.rmart.utilits.pojos.ImageURLResponse;
import com.rmart.utilits.pojos.ProductResponse;

import java.util.ArrayList;

public class ProductFromInventorySearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   public ArrayList<Product> products;
    Context context;
    public OnInventoryClickedListener mListener;
   public ProductFromInventorySearchListAdapter(Context context, ArrayList<Product> products, OnInventoryClickedListener mListener)
   {
       this.products=products;
       this.context=context;
       this.mListener=mListener;

   }
    public void addProducts(ArrayList<Product> productDatas){
        this.products.addAll(productDatas);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowProductStatusBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_row_product_status, parent, false);

        ProductItemViewHolder vh = new ProductItemViewHolder(binding); // pass the view to View Holder
        return vh;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       if(holder instanceof  ProductItemViewHolder) {

           ProductItemViewHolder productItemViewHolder = (ProductItemViewHolder) holder;
           Product product =products.get(position);
           productItemViewHolder.bind(product);
           productItemViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   ProductResponse productResponse = new ProductResponse();
                   productResponse.setBrand(product.getBrandName());
                   productResponse.setProductID(product.getProductId()+"");
                   productResponse.setBrandID(product.getBrandId()+"");
                   productResponse.setCategory(product.getCategoryName());
                   productResponse.setCategoryID(product.getProductCatId()+"");
                   productResponse.setProductName(product.getProductName());
                   productResponse.setProductImage(product.getDisplayImage());
                   productResponse.setDescription(product.getProductDetails());
                   productResponse.setProductLibID(product.getProductId()+"");
                   ArrayList<ImageURLResponse> imageURLResponses =new ArrayList<>();
                   for (ProductImage productImage:product.getImages()) {
                       ImageURLResponse imageURLResponse =  new ImageURLResponse();
                       imageURLResponse.setImageURL(productImage.getImage());
                       imageURLResponse.setImageID(productImage.getImageId()+"");
                       imageURLResponse.setImageName(productImage.getImageName());
                       imageURLResponse.setPrimary(productImage.getIsPrimary());
                       imageURLResponses.add(imageURLResponse);
                   }
                   productResponse.setImageDataObject(imageURLResponses);

                   mListener.showProductPreview(productResponse, true);

               }
           });


       }

    }

    @Override
    public int getItemCount() {
        return products!=null?products.size():0;
    }


    public class ProductItemViewHolder extends RecyclerView.ViewHolder {

        ItemRowProductStatusBinding binding;

        public ProductItemViewHolder(ItemRowProductStatusBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

        }

        public void bind(Object obj) {
              binding.setVariable(BR.product, obj);
              binding.executePendingBindings();
        }
    }
}
