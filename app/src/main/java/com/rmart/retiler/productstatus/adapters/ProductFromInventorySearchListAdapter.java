package com.rmart.retiler.productstatus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.ItemRowProductStatusBinding;
import com.rmart.inventory.OnInventoryClickedListener;
import com.rmart.inventory.models.UpdateReasponce;
import com.rmart.retiler.inventory.product_from_inventory.model.Product;
import com.rmart.retiler.inventory.product_from_inventory.model.ProductImage;
import com.rmart.retiler.inventory.product_from_inventory.repositories.ProductFromInventroyListRepository;
import com.rmart.retiler.productstatus.UdateListner;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.pojos.ImageURLResponse;
import com.rmart.utilits.pojos.ProductResponse;

import java.util.ArrayList;

public class ProductFromInventorySearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   public ArrayList<Product> products;
    Context context;
    UdateListner udateListner;
    public OnInventoryClickedListener mListener;
   public ProductFromInventorySearchListAdapter(Context context, ArrayList<Product> products, OnInventoryClickedListener mListener,UdateListner udateListner)
   {
       this.products=products;
       this.context=context;
       this.mListener=mListener;
       this.udateListner=udateListner;

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
           productItemViewHolder.binding.tvEdit.setOnClickListener(new View.OnClickListener() {
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
            if(product.getUnits()!=null && product.getUnits().size()>0){

                productItemViewHolder.bindUnit(product.getUnits().get(0));
            }



           productItemViewHolder.binding.sweetch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   productItemViewHolder.binding.sweetch.setVisibility(View.GONE);
                   productItemViewHolder.binding.progressBar1.setVisibility(View.VISIBLE);
                   productItemViewHolder.binding.sweetch.setOnCheckedChangeListener(null);
                   MutableLiveData<UpdateReasponce> resultMutableLiveData= ProductFromInventroyListRepository.isactive(product.getProductId()+"",b?"YES":"NO","2");
                   resultMutableLiveData.observeForever(data -> {
                       if(data.getStatus()!=null && data.getStatus().equalsIgnoreCase("Success")) {


                           products.remove(product);
                           notifyDataSetChanged();
                           udateListner.onUpdate();



                       } else {
                           notifyDataSetChanged();
                       }
                       productItemViewHolder.binding.sweetch.setVisibility(View.VISIBLE);
                       productItemViewHolder.binding.progressBar1.setVisibility(View.GONE);

                       Toast.makeText(context,data.getMsg(),Toast.LENGTH_LONG).show();
                   });

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
        public void bindUnit(Object obj) {
            binding.setVariable(BR.unit, obj);
            binding.executePendingBindings();
        }
    }
}