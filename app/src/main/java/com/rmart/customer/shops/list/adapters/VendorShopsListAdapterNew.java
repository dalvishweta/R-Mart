package com.rmart.customer.shops.list.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.PrecomputedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.shops.list.models.ShopDetailsModel;
import com.rmart.databinding.ItemLoadingBinding;
import com.rmart.databinding.LoadMoreItemBinding;
import com.rmart.databinding.LoadMoreItemRowBinding;
import com.rmart.databinding.ShopItemRowBinding;
import com.rmart.databinding.ShopItemRowBindingImpl;
import com.rmart.deeplinking.LinkGenerator;
import com.rmart.utilits.Permisions;
import com.rmart.utilits.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class VendorShopsListAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final static int LOADMORE_VIEW=0000202;
    final static int LOADING_VIEW=0002202;
     boolean isloading = false;
    public boolean isLastPage = false;
    public List<ShopDetailsModel> filteredListData;
    private final CallBackInterface callBackListener;
    Activity context;
    public void setLoading(boolean loading) {
        isloading = loading;
        notifyItemChanged(filteredListData.size());
    }


    public VendorShopsListAdapterNew(Activity context, List<ShopDetailsModel> productList, CallBackInterface callBackListener) {
        this.context = context;
        filteredListData = new ArrayList<>();
        this.filteredListData.addAll(productList);
        this.callBackListener = callBackListener;
    }

    public void updateItems(List<ShopDetailsModel> listData) {

        this.filteredListData.addAll(listData);
    }

    @Override
    public int getItemViewType(int position) {
        if(filteredListData!=null && filteredListData.size() ==position) {


            return  isloading?LOADING_VIEW:LOADMORE_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(LOADMORE_VIEW == viewType){
            LoadMoreItemRowBinding loadMoreItemBinding =DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.load_more_item_row, parent, false);
            return new LoadMoreViewHolder(loadMoreItemBinding);
        }
        else  if(LOADING_VIEW == viewType){

            ItemLoadingBinding itemLoadingBinding =DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        } else {
            ShopItemRowBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.shop_item_row, parent, false);

            MyViewHolder vh = new MyViewHolder(binding); // pass the view to View Holder
            return vh;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder2, int position) {

        if(holder2 instanceof MyViewHolder) {
            MyViewHolder holder = (MyViewHolder) holder2;
            final ShopDetailsModel dataModel = filteredListData.get(position);
            holder.binding.getRoot().setOnClickListener(view -> {
                callBackListener.callBackReceived(dataModel);
            });
            holder.binding.ivCallField.setOnClickListener(view -> {
                Utils.openDialPad(view.getContext(), dataModel.getShopMobileNo());
            });
            holder.binding.ivMessageField.setOnClickListener(view -> {
                Utils.openGmailWindow(view.getContext(), dataModel.getEmailId());
            });
            holder.binding.ivFavouriteImage.setOnClickListener(view -> {
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_SHOP_FAVOURITE);
                contentModel.setValue(dataModel);
                callBackListener.callBackReceived(contentModel);
            });
            holder.binding.ivShareField.setOnClickListener(view -> {
                if (Permisions.checkWriteExternlStoragePermission((Activity) holder.binding.getRoot().getContext())) {
                    Bitmap bitmap = null;
                    try {
                        holder.binding.imageview.setDrawingCacheEnabled(true);
                        bitmap = Bitmap.createBitmap(holder.binding.imageview.getDrawingCache());
                        holder.binding.imageview.setDrawingCacheEnabled(false);

                    } catch (Exception e) {
                        Toast.makeText(holder.binding.imageview.getContext(), "pont1", Toast.LENGTH_LONG).show();
                    }

                    String message = "रोकड मार्ट आता आपल्या शहरामध्ये!!!\n" +
                            "आता " + dataModel.getShopName() + "शॉप रोकड मार्ट सोबत ऑनलाईन झाले आहे. \n" +
                            "नवीन ऑफर्स आणि शॉपिंग साठी खालील लिंक वर क्लिक करा आणि अँप डाउनलोड करा.\n" + "RokadMart brings your nearest local store online." + dataModel.getShopName() + "  is now online.Buy all the products on RokadMart with home delivery ";
                    String deeplink = "https://www.rokadmart.com/public/Home/index?shop_id=" + dataModel.getShopId() + "&client_id=2&created_by=" + dataModel.getVendorId();
                    LinkGenerator.shareLink(context, message, bitmap, deeplink);


                } else {
                    Permisions.requestWriteExternlStoragePermission(holder.binding.getRoot().getContext());
                }
            });
            holder.bind(dataModel);
        }
        if(holder2 instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder2;
            loadMoreViewHolder.binding.topview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBackListener.callBackReceived(null);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(filteredListData!=null) {
            if(isLastPage ){
                return filteredListData.size() ;
            }
            return filteredListData.size() +1;
        } else {
            return  0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ShopItemRowBinding binding;
        public MyViewHolder(ShopItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
        public void bind(Object obj) {
            binding.setVariable(BR.shopdata , obj);
           binding.executePendingBindings();
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder {


        ItemLoadingBinding binding;
        public LoadingViewHolder(ItemLoadingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
        public void bind(Object obj) {
            binding.setVariable(BR.shopdata , obj);
            binding.executePendingBindings();
        }
    }
    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {


        LoadMoreItemRowBinding binding;
        public LoadMoreViewHolder(LoadMoreItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
        public void bind(Object obj) {
            binding.setVariable(BR.shopdata , obj);
            binding.executePendingBindings();
        }
    }
}
